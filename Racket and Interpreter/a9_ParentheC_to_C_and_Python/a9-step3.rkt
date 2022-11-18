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


(define value-of-cps
  (lambda (exp env-cps k-cps)
    (union-case exp expr
      
      [(const cexp)
       (apply-k k-cps cexp)]
      
      [(mult nexp1 nexp2)
       (value-of-cps nexp1 env-cps
                     (mult-outer-k env-cps nexp2 k-cps))]
      
      [(sub1 nexp)
       (value-of-cps nexp env-cps
                     (just-k sub1 k-cps))]
      
      [(zero nexp)
       (value-of-cps nexp env-cps
                     (just-k zero? k-cps))]
      
      [(if test conseq alt)
       (value-of-cps test env-cps
                     (if-k env-cps conseq alt k-cps))]
      
      [(letcc body)
       (value-of-cps body
                     (envr_extend-env env-cps k-cps)
                     k-cps)]
      
      [(throw kexp vexp)
       (value-of-cps kexp env-cps
                     (throw-outer-k env-cps vexp))]
      
      [(let exp body)
       (value-of-cps exp env-cps
                     (let-k env-cps body k-cps))]
      
      [(var n)
       (apply-env env-cps n k-cps)]
      
      [(lambda body)
       (apply-k k-cps
                (clos_closure body env-cps))]
      
      [(app rator rand)
       (value-of-cps rator env-cps
                     (app-outer-k env-cps rand k-cps))]
      )))


(define just-k
  (lambda (a^ k^)
    `(just-k ,a^ ,k^)))


(define mult-outer-k
  (lambda (env-cps^ x2^ k^)
    `(mult-outer-k ,env-cps^ ,x2^ ,k^)))


(define mult-inner-k
  (lambda (v^ k^)
    `(mult-inner-k ,v^ ,k^)))


(define if-k
  (lambda (env-cps^ conseq^ alt^ k^)
    `(if-k ,env-cps^ ,conseq^ ,alt^ ,k^)))


(define throw-outer-k
  (lambda (env-cps^ k-exp^)
    `(throw-outer-k ,env-cps^ ,k-exp^)))


(define throw-inner-k
  (lambda (v^ env-cps^)
    `(throw-inner-k ,v^ ,env-cps^)))


(define let-k
  (lambda (env-cps^ body^ k^)
    `(let-k ,env-cps^ ,body^ ,k^)))


(define app-outer-k
  (lambda (env-cps^ rand^ k^)
    `(app-outer-k ,env-cps^ ,rand^ ,k^)))


(define app-inner-k
  (lambda (v^ k^)
    `(app-inner-k ,v^ ,k^)))


(define apply-k
  (lambda (k val)
    (match k
      
      [`(empty-k)
       val]
      
      [`(just-k ,a ,k)
       (apply-k k (a val))]
      
      [`(mult-outer-k ,env-cps ,x ,k)
       (value-of-cps x env-cps
                     (mult-inner-k val k))]
      
      [`(mult-inner-k ,v ,k)
       (apply-k k (* v val))]
      
      [`(if-k ,env-cps ,conseq ,alt ,k)
       (if val
           (value-of-cps conseq env-cps k)
           (value-of-cps alt env-cps k))]
      
      [`(throw-outer-k ,env-cps ,k-exp)
       (value-of-cps k-exp env-cps
                     (throw-inner-k val env-cps))]
      
      [`(throw-inner-k ,v ,env-cps)
       (apply-k v val)]
      
      [`(let-k ,env-cps ,body ,k)
       (value-of-cps body
                     (envr_extend-env env-cps val) 
                     k)]
      
      [`(app-outer-k ,env-cps ,rand ,k)
       (value-of-cps rand env-cps
                     (app-inner-k val k))]
      
      [`(app-inner-k ,v ,k)
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
    '(empty-k)))



;Test for main
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