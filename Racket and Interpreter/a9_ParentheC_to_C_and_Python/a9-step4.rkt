#lang racket
(require "parenthec.rkt")

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
  (extend-env env v))

(define-union kt
  (empty-k)
  (just-k a k)
  (mult-outer-k env-cps x k)
  (mult-inner-k v k)
  (if-k env-cps conseq alt k)
  (let-k env-cps body k)
  (throw-outer-k env-cps k-exp)
  (throw-inner-k v env-cps)
  (app-outer-k env-cps rand k)
  (app-inner-k v k))


(define value-of-cps
  (lambda (exp env-cps k-cps)
    (union-case exp expr
      
      [(const cexp)
       (apply-k k-cps cexp)]
   
      [(mult nexp1 nexp2)
       (value-of-cps nexp1 env-cps
                     (kt_mult-outer-k env-cps nexp2 k-cps))]
      
      [(sub1 nexp)
       (value-of-cps nexp env-cps
                     (kt_just-k sub1 k-cps))]
      
      [(zero nexp)
       (value-of-cps nexp env-cps
                     (kt_just-k zero? k-cps))]
      
      [(if test conseq alt)
       (value-of-cps test env-cps
                     (kt_if-k env-cps conseq alt k-cps))]
      
      [(letcc body)
       (value-of-cps body
                     (envr_extend-env env-cps k-cps)
                     k-cps)]
      
      [(throw kexp vexp)
       (value-of-cps kexp env-cps
                     (kt_throw-outer-k env-cps vexp))]
      
      [(let exp body)
       (value-of-cps exp env-cps
                     (kt_let-k env-cps body k-cps))]
      
      [(var n)
       (apply-env env-cps n k-cps)]
      
      [(lambda body)
       (apply-k k-cps
                (clos_closure body env-cps))]
      
      [(app rator rand)
       (value-of-cps rator env-cps
                     (kt_app-outer-k env-cps rand k-cps))]
      )))
  

(define apply-k
  (lambda (k val)
    (union-case k kt
      
      [(empty-k)
       val]
      
      [(just-k a k)
       (apply-k k (a val))]
      
      [(mult-outer-k env-cps x k)
       (value-of-cps x env-cps
                     (kt_mult-inner-k val k))]
     
      [(mult-inner-k v k)
       (apply-k k (* v val))]
      
      [(if-k env-cps conseq alt k)
       (if val
           (value-of-cps conseq env-cps k)
           (value-of-cps alt env-cps k))]
      
      [(throw-outer-k env-cps k-exp)
       (value-of-cps k-exp env-cps
                     (kt_throw-inner-k val env-cps))]
      
      [(throw-inner-k v env-cps)
       (apply-k v val)]
      
      [(let-k env-cps body k)
       (value-of-cps body
                     (envr_extend-env env-cps val)
                     k)]
      
      [(app-outer-k env-cps rand k)
       (value-of-cps rand env-cps
                     (kt_app-inner-k val k))]
      
      [(app-inner-k v k)
       (apply-closure v val k)]
      )))


(define apply-env
  (lambda (env a k)
    (union-case env envr
                [(empty) (error 'value-of "unbound identifier")]
                [(extend-env env v)
                 (if (zero? a)
                     (apply-k k v)
                     (apply-env env (sub1 a) k))]
                )))


(define apply-closure
  (lambda (rator rand k)
    (union-case rator clos
                [(closure body env-cps)
                 (value-of-cps body
                               (envr_extend-env env-cps rand)
                               k)])))


(define extend-env
  (lambda (env v)
    `(extend-env ,v ,env)))


(define empty-env
  (lambda ()
    (envr_empty)))
 

(define empty-k
  (lambda ()
    (kt_empty-k)))

;Test for main:
(define main 
  (lambda ()
    (value-of-cps 
     (expr_let 
      (expr_lambda
       (expr_lambda 
        (expr_if
         (expr_zero (expr_var 0))
         (expr_const 1)
         (expr_mult (expr_var 0) (expr_app (expr_app (expr_var 1) (expr_var 1)) (expr_sub1 (expr_var 0)))))))
      (expr_mult
       (expr_letcc
        (expr_app
         (expr_app (expr_var 1) (expr_var 1))
         (expr_throw (expr_var 0) (expr_app (expr_app (expr_var 1) (expr_var 1)) (expr_const 4)))))
       (expr_const 5)))
     (empty-env)
     (empty-k))))


(main)