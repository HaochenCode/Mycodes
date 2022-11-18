;#lang racket
;(require "parenthec.rkt")
(define-program-counter pc)


(define-registers
  *exp*
  *env*
  *k*
  *rator*
  *r2* 
  *v*
  *vari*)


(define-union expr
  (const cexp)
  (var n)
  (if test conseq alt)
  (mult nexp1 nexp2)
  (sub1 nexp)
  (zero nexp)
  (letcc body)
  (throw kexp vexp)
  (let exp body)              
  (lambda body)
  (app rator r2))


(define-union clos
  (closure body env-cps))


(define-union envr
  (empty)
  (extend-env v env))


(define-union kt
  (empty-k dismount)
  (sub1-k k)
  (zero-k k)
  (mult-outer-k env-cps x k)
  (mult-inner-k v k)
  (if-k env-cps conseq alt k)
  (let-k env-cps body k)
  (throw-outer-k env-cps k-exp)
  (throw-inner-k v env-cps)
  (app-outer-k env-cps r2 k)
  (app-inner-k v k))


(define-label value-of-cps
  (union-case *exp* expr
              
              [(const cexp)
               (begin
                 (set! *v* cexp)
                 (set! pc apply-k))]
              
              [(mult nexp1 nexp2)
               (begin
                 (set! *exp* nexp1)
                 (set! *k* (kt_mult-outer-k *env* nexp2 *k*))
                 (set! pc value-of-cps))]
             
              [(sub1 nexp)
               (begin
                 (set! *exp* nexp)
                 (set! *k* (kt_sub1-k *k*))
                 (set! pc value-of-cps))]
              
              [(zero nexp)
               (begin
                 (set! *exp* nexp)
                 (set! *k* (kt_zero-k *k*))
                 (set! pc value-of-cps))]
              
              [(if test conseq alt)
               (begin
                 (set! *exp* test)
                 (set! *k* (kt_if-k *env* conseq alt *k*))
                 (set! pc value-of-cps))]
              
              [(letcc body)
               (begin
                 (set! *exp* body)
                 (set! *env* (envr_extend-env *k* *env*))
                 (set! *k* *k*)
                 (set! pc value-of-cps))]
              
              [(throw kexp vexp)
               (begin
                 (set! *exp* kexp)
                 (set! *k* (kt_throw-outer-k *env* vexp))
                 (set! pc value-of-cps))]
              
              [(let expr body)
               (begin
                 (set! *exp* expr)
                 (set! *k* (kt_let-k *env* body *k*))
                 (set! pc value-of-cps))]
              
              [(var n)
               (begin
                 (set! *vari* n)
                 (set! pc apply-env))]
             
              [(lambda body)
               (begin
                 (set! *exp* *exp*)
                 (set! *v* (clos_closure body *env*))
                 (set! pc apply-k))]
              
              [(app rator r2)
               (begin
                 (set! *exp* rator)
                 (set! *k* (kt_app-outer-k *env* r2 *k*))
                 (set! pc value-of-cps))]
              ))


(define-label apply-k
  (union-case *k* kt
      
      [(empty-k dismount)
       (dismount-trampoline dismount)]
     
      [(sub1-k k^)
       (begin
         (set! *k* k^)
         (set! *v* (sub1 *v*))
         (set! pc apply-k))]
  
      [(zero-k k^)
       (begin
         (set! *k* k^)
         (set! *v* (zero? *v*))
         (set! pc apply-k))]
      
      [(mult-outer-k env-cps x k^)
       (begin
         (set! *exp* x)
         (set! *env* env-cps)
         (set! *k* (kt_mult-inner-k *v* k^))
         (set! pc value-of-cps))]
      
      [(mult-inner-k v^ k^)
       (begin
         (set! *k* k^)
         (set! *v* (* v^ *v*))
         (set! pc apply-k))]
      
      [(if-k env-cps conseq alt k^)
       (if *v*
           (begin
             (set! *exp* conseq)
             (set! *env* env-cps)
             (set! *k* k^)
             (set! pc value-of-cps))
           (begin
             (set! *exp* alt)
             (set! *env* env-cps)
             (set! *k* k^)
             (set! pc value-of-cps)))]
      
      [(throw-outer-k env-cps k-exp)
       (begin
         (set! *exp* k-exp)
         (set! *env* env-cps)
         (set! *k* (kt_throw-inner-k *v* *env*))
         (set! pc value-of-cps))]
      
      [(throw-inner-k v env-cps)
       (begin
         (set! *exp* *exp*)
         (set! *env* *env*)
         (set! *k* v)
         (set! pc apply-k))]
      
      [(let-k env-cps body k^)
       (begin
         (set! *exp* body)
         (set! *env* (envr_extend-env *v* env-cps))
         (set! *k* k^)
         (set! pc value-of-cps))]
      
      [(app-outer-k env-cps r2 k^)
       (begin
         (set! *exp* r2)
         (set! *env* env-cps)
         (set! *k* (kt_app-inner-k *v* k^))
         (set! pc value-of-cps))]
      
      [(app-inner-k v^ k^)
       (begin
         (set! *exp* *exp*)
         (set! *env* *env*)
         (set! *r2* *v*)
         (set! *rator* v^)
         (set! *k* k^)
         (set! pc apply-closure))]
      ))


(define-label apply-env
    (union-case *env* envr
                [(empty) (error 'value-of-cps "unbound identifier")]
                [(extend-env re env^)
                 (if (zero? *vari*)
                     (begin
                       (set! *k* *k*)
                       (set! *v* re)
                       (set! pc apply-k))
                     (begin
                       (set! *k* *k*)
                       (set! *env* env^)
                       (set! *vari* (sub1 *vari*))
                       (set! pc apply-env)))]
                ))

(define-label apply-closure
    (union-case *rator* clos
                [(closure body env-cps)
                 (begin
                   (set! *exp* body)
                   (set! *env* (envr_extend-env *r2* env-cps))
                   (set! *k* *k*)
                   (set! pc value-of-cps))]
                ))

(define-label main
  (begin 
      (set! *exp* (expr_let 
                 (expr_lambda
                  (expr_lambda 
                   (expr_if
                    (expr_zero (expr_var 0))
                    (expr_const 1)
                    (expr_mult (expr_var 0)
                               (expr_app (expr_app (expr_var 1) (expr_var 1))
                                         (expr_sub1 (expr_var 0)))))))
                 (expr_mult
                  (expr_letcc
                   (expr_app
                    (expr_app (expr_var 1) (expr_var 1))
                    (expr_throw (expr_var 0)
                                (expr_app (expr_app (expr_var 1)
                                                    (expr_var 1)) (expr_const 4)))))
                  (expr_const 5))))
      
      (set! *env* (envr_empty))
      (set! pc value-of-cps)
      (mount-trampoline kt_empty-k *k* pc)
(printf "Fact 5: ~s\n" *v*)))
;(main)