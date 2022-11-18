#lang racket
; Part 1:

(define value-of-ds
  (lambda (e env)
    (match e
      [`, n #:when (number? n) n]
      [`, b #:when (boolean? b) b]
      [`(+ ,nexpr1 ,nexpr2) (+ (value-of-ds nexpr1 env) (value-of-ds nexpr2 env))]
      [`(* ,op1 ,op2) (* (value-of-ds op1 env) (value-of-ds op2 env))]
      [`(zero?,v) (= (value-of-ds v env) 0)]
      [`(sub1, s) (- (value-of-ds s env) 1)]
      [`(if,condition ,yes ,no)(if(value-of-ds condition env)
                                 (value-of-ds yes env)
                                 (value-of-ds no env))]
      [`(let ((,x ,y)) ,body)
       (let ([z (value-of-ds y env)])
         (value-of-ds body (extend-env-ds x z env)))]
      [`,y #:when (symbol? y) (apply-env-ds env y)]
      [`(lambda (,x) ,body)
       #:when (symbol? x)
       (make-closure-ds x env body)]
      [`(,rator ,rand)
       (apply-closure-ds (value-of-ds rator env) (value-of-ds rand env))])))
      

(define apply-closure-ds
  (lambda (rator rand)
    (match rator
      [`(make-closure-ds ,x ,env ,body)
       (value-of-ds body (extend-env-ds x rand env))])))

(define make-closure-ds
  (lambda (x env body)
    `(make-closure-ds ,x ,env ,body)))

(define apply-env-ds
  (lambda (env y)
    (match env
      [`(extend-env-ds ,x ,arg ,env)
       (cond
        [(eqv? y x) arg]
        [else (apply-env-ds env y)])]
      [`(empty-env-ds)
       (error "Can't find val of " y)])))  

(define extend-env-ds
  (lambda (x arg env)
    `(extend-env-ds ,x ,arg ,env)))

(define empty-env-ds
  (lambda ()
    `(empty-env-ds)))

;Part 1 Tests:
(display "Part 1 tests: \n")

(value-of-ds
   '((lambda (x) (if (zero? x)
                     #t
                     #f))
     0)
   (empty-env-ds))

(value-of-ds
   '((lambda (a) (if (zero? a) 
                     12 
                     47)) 
     0) 
   (empty-env-ds))

(value-of-ds
   '(let ([y (* 3 4)])
      ((lambda (x) (* x y)) (sub1 6)))
   (empty-env-ds))

(value-of-ds
   '(let ([x (* 2 3)])
      (let ([y (sub1 x)])
        (* x y)))
   (empty-env-ds))

(value-of-ds
   '(let ([x (* 2 3)])
      (let ([x (sub1 x)])
        (* x x)))
   (empty-env-ds))

(value-of-ds
   '(((lambda (f)
        (lambda (n)
          (if (zero? n) 1 (* n ((f f) (sub1 n))))))
      (lambda (f)
        (lambda (n)
          (if (zero? n) 1 (* n ((f f) (sub1 n)))))))
     5)
   (empty-env-ds))

;Part 2:
(define value-of-ds
  (lambda (e env)
    (match e
      [`, n #:when (number? n) n]
      [`, b #:when (boolean? b) b]
      [`(+ ,nexpr1 ,nexpr2) (+ (value-of-ds nexpr1 env) (value-of-ds nexpr2 env))]
      [`(* ,op1 ,op2) (* (value-of-ds op1 env) (value-of-ds op2 env))]
      [`(zero?,v) (= (value-of-ds v env) 0)]
      [`(sub1, s) (- (value-of-ds s env) 1)]
      [`(if,condition ,yes ,no)(if(value-of-ds condition env)
                                 (value-of-ds yes env)
                                 (value-of-ds no env))]
      [`(let ((,x ,y)) ,body)
       (let ([z (value-of-ds y env)])
         (value-of-ds body (extend-env-ds x z env)))]
      [`,y #:when (symbol? y) (apply-env-ds env y)]
      [`(lambda (,x) ,body)
       #:when (symbol? x)
       (make-closure-ds x env body)]
      [`(,rator ,rand)
       (apply-closure-ds (value-of-ds rator env) (value-of-ds rand env))])))
      
(define apply-closure-ds
  (lambda (rator rand)
    (match rator
      [`(make-closure-ds ,x ,env ,body)
       (value-of-ds body (extend-env-ds x rand env))])))

(define make-closure-ds
  (lambda (x env body)
    `(make-closure-ds ,x ,env ,body)))

(define apply-env-ds
  (lambda (env y)
    (match env
      [`(extend-env-ds ,x ,arg ,env)
       (cond
        [(eqv? y x) arg]
        [else (apply-env-ds env y)])]
      [`(empty-env-ds)
       (error "Can't find val of " y)])))  

(define extend-env-ds
  (lambda (x arg env)
    `(extend-env-ds ,x ,arg ,env)))

(define empty-env-ds
  (lambda ()
    `(empty-env-ds)))

