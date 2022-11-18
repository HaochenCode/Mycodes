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



(define value-of-cps
  (lambda (exp env-cps k)
    (union-case exp expr
      [(const expr)
       (apply-k k expr)]
      [(mult x1 x2)
       (value-of-cps x1 env-cps
                     (mult-outer-k env-cps x2 k))]
      [(sub1 x)
       (value-of-cps x env-cps
                     (normal-k sub1 k))]
      [(zero x)
       (value-of-cps x env-cps
                     (normal-k zero? k))]
      [(if test conseq alt)
       (value-of-cps test env-cps
                     (if-k env-cps conseq alt k))]
      [(letcc body)
       (value-of-cps body
                     (extend-env env-cps k)
                     k)]
      [(throw k-exp v-exp)
       (value-of-cps k-exp env-cps
                     (throw-outer-k env-cps v-exp))]
      [(let e body)
       (value-of-cps e env-cps
                     (let-k env-cps body k))]
      [(var expr)
       (apply-env env-cps expr k)]
      [(lambda body)
       (apply-k k
                (make-closure body env-cps))]
      [(app rator rand)
       (value-of-cps rator env-cps
                     (app-outer-k env-cps rand k))]
      )))

(define normal-k
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
      [`(if-k ,env-cps ,condi ,yes ,k)
       (if val
           (value-of-cps condi env-cps k)
           (value-of-cps yes env-cps k))]
      [`(throw-outer-k ,env-cps ,k-exp)
       (value-of-cps k-exp env-cps
                     (throw-inner-k val env-cps))]
      [`(throw-inner-k ,v ,env-cps)
       (apply-k v val)]
      [`(let-k ,env-cps ,body ,k)
       (value-of-cps body
                     (extend-env env-cps val)
                     k)]
      [`(app-outer-k ,env-cps ,rand ,k)
       (value-of-cps rand env-cps
                     (app-inner-k val k))]
      [`(app-inner-k ,v ,k)
       (apply-closure v val k)]
      )))

(define apply-env
  (lambda (env a k)
    (match env
      [`(empty-env) a]
      [`(extend-env ,v ,env)
       (if (zero? a)
           (apply-k k v)
           (apply-env env (sub1 a) k))]
      )))

(define make-closure
  (lambda (body env)
    `(closure ,body ,env)))

(define apply-closure
  (lambda (rator rand k)
    (match rator
      [`(closure ,body ,env-cps)
       (value-of-cps body
                     (extend-env env-cps rand)
                     k)])))

(define extend-env
  (lambda (env v)
    `(extend-env ,v ,env)))

(define empty-env
  (lambda ()
    `(empty-env '(error 'value-of "unbound identifier"))))
 
(define empty-k
  (lambda ()
    '(empty-k)))


;Test for main function
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


