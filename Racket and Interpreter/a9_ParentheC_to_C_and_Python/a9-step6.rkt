#lang racket
(require "parenthec.rkt")

(define-registers
  *exp*
  *env*
  *k*
  *rator*
  *rand*
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
  (app rator rand))


(define-union clos
  (closure body env-cps))


(define-union envr
  (empty)
  (extend-env v env))


(define-union kt
  (empty-k)
  (sub1-k k)
  (zero-k k)
  (mult-outer-k env-cps x k)
  (mult-inner-k v k)
  (if-k env-cps conseq alt k)
  (let-k env-cps body k)
  (throw-outer-k env-cps k-exp)
  (throw-inner-k v env-cps)
  (app-outer-k env-cps rand k)
  (app-inner-k v k))


(define value-of-cps
  (lambda ()
  (union-case *exp* expr
            
              [(const cexp)
              
               (begin
                 (set! *v* cexp)
                 (apply-k))]
          
              [(mult nexp1 nexp2)
              
               (begin
                 (set! *exp* nexp1)
                 (set! *k* (kt_mult-outer-k *env* nexp2 *k*))
                 (value-of-cps))]
              
              [(sub1 nexp)
               
               (begin
                 (set! *exp* nexp)
                 (set! *k* (kt_sub1-k *k*))
                 (value-of-cps))]
              
              [(zero nexp)
               
               (begin
                 (set! *exp* nexp)
                 (set! *k* (kt_zero-k *k*))
                 (value-of-cps))]
              
              [(if test conseq alt)
               
               (begin
                 (set! *exp* test)
                 (set! *k* (kt_if-k *env* conseq alt *k*))
                 (value-of-cps))]
              
              [(letcc body)
               
               (begin
                 (set! *exp* body)
                 (set! *env* (envr_extend-env *k* *env*))
                 (set! *k* *k*)
                 (value-of-cps))]
              
              [(throw kexp vexp)
               
               (begin
                 (set! *exp* kexp)
                 (set! *k* (kt_throw-outer-k *env* vexp))
                 (value-of-cps))]
              
              [(let expr body)
               
               (begin
                 (set! *exp* expr)
                 (set! *k* (kt_let-k *env* body *k*))
                 (value-of-cps))]
              
              [(var n)
              
               (begin
                 (set! *vari* n)
                 (apply-env))]
              
              [(lambda body)
              
               (begin
                 (set! *exp* *exp*)
                 (set! *v* (clos_closure body *env*))
                 (apply-k))]
        
              [(app rator rand)
               
               (begin
                 (set! *exp* rator)
                 (set! *k* (kt_app-outer-k *env* rand *k*))
                 (value-of-cps))]
              )))


(define apply-k
  (lambda ()
  (union-case *k* kt

      [(empty-k)
       *v*]

      [(sub1-k k^)
 
       (begin
         (set! *k* k^)
         (set! *v* (sub1 *v*))
         (apply-k))]
    
      [(zero-k k^)
    
       (begin
         (set! *k* k^)
         (set! *v* (zero? *v*))
         (apply-k))]
      
      [(mult-outer-k env-cps x k^)

       (begin
         (set! *exp* x)
         (set! *env* env-cps)
         (set! *k* (kt_mult-inner-k *v* k^))
         (value-of-cps))]

      [(mult-inner-k v^ k^)
       
       (begin
         (set! *k* k^)
         (set! *v* (* v^ *v*))
         (apply-k))]
     
      [(if-k env-cps conseq alt k^)
       (if *v*
          
           (begin
             (set! *exp* conseq)
             (set! *env* env-cps)
             (set! *k* k^)
             (value-of-cps))
           
           (begin
             (set! *exp* alt)
             (set! *env* env-cps)
             (set! *k* k^)
             (value-of-cps)))]
      
      [(throw-outer-k env-cps k-exp)
     
       (begin
         (set! *exp* k-exp)
         (set! *env* env-cps)
         (set! *k* (kt_throw-inner-k *v* *env*))
         (value-of-cps))]
     
      [(throw-inner-k v env-cps)
      
       (begin
         (set! *exp* *exp*)
         (set! *env* *env*)
         (set! *k* v)
         (apply-k))]
      
      [(let-k env-cps body k^)
       
       (begin
         (set! *exp* body)
         (set! *env* (envr_extend-env *v* env-cps))
         (set! *k* k^)
         (value-of-cps))]
      
      [(app-outer-k env-cps rand k^)
      
       (begin
         (set! *exp* rand)
         (set! *env* env-cps)
         (set! *k* (kt_app-inner-k *v* k^))
         (value-of-cps))]
      
      [(app-inner-k v^ k^)
       
       (begin
         (set! *exp* *exp*)
         (set! *env* *env*)
         (set! *rand* *v*)
         (set! *rator* v^)
         (set! *k* k^)
         (apply-closure))]
      )))


(define apply-env
  (lambda ()
    (union-case *env* envr
                [(empty) (error 'value-of-cps "unbound identifier")]
                [(extend-env re env^)
                 (if (zero? *vari*)
                     (begin
                       (set! *k* *k*)
                       (set! *v* re)
                       (apply-k))
                     (begin
                       (set! *k* *k*)
                       (set! *env* env^)
                       (set! *vari* (sub1 *vari*))
                       (apply-env)))]
                )))


(define apply-closure
  (lambda ()
    (union-case *rator* clos
                [(closure body env-cps)
                 (begin
                   (set! *exp* body)
                   (set! *env* (envr_extend-env *rand* env-cps))
                   (set! *k* *k*)
                   (value-of-cps))]
                )))

(define driver
  (lambda (expression)
    (begin (set! *exp* expression)
           (set! *k* (kt_empty-k))
           (set! *env* (envr_empty))
           (value-of-cps))))

;Test for main
(define main
  (lambda ()
    (driver (expr_let 
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
                                         (expr_var 1)) (expr_const 4)))))(expr_const 5))))))

(main)