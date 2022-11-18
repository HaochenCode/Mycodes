#lang racket
;Problem 1:
(define debruijn
  (λ (v cenv)
    (match cenv
      [`() `,(error "Variable not found in cenv" v)] 
      [`(,a . ,d) #:when (eqv? a v) 0]
      [else (add1 (debruijn v (cdr cenv)))])))

; Helper function
(define check-free?
  (λ(x ls)
    (cond[(null? ls) #t]
         [(eqv? x (car ls)) #f]
         [else (check-free? x (cdr ls))])))


(define lex
  (λ (e cenv)
    (match e
      [`,y #:when (symbol? y) (cond
                                [(check-free? y cenv) y]
                                [else `(var,(debruijn y cenv))])] 
      [`(lambda (,x) ,body)
       `(lambda ,(lex body (cons x cenv)))]
      [`(,rator ,rand)
       `(,(lex rator cenv) ,(lex rand cenv))])))


;Test for part1:
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
(display"Tests for P1: \n")
(lex '(lambda (x) x) 
       '())
(lex '(lambda (y) (lambda (x) y)) 
       '())
(lex '(lambda (y) (lambda (x) (x y))) 
       '())
(lex '(lambda (x) (lambda (x) (x x))) 
       '())
(lex '(lambda (x) (lambda (x) (y x))) 
       '())
(lex '(lambda (y) ((lambda (x) (x y)) (lambda (c) (lambda (d) (y c))))) 
       '())
(lex '(lambda (a)
          (lambda (b)
            (lambda (c)
              (lambda (a)
                (lambda (b)
                  (lambda (d)
                    (lambda (a)
                      (lambda (e)
                        (((((a b) c) d) e) a))))))))) 
       '())
(lex '(lambda (a)
          (lambda (b)
	    (lambda (c)
	      (lambda (w)
	        (lambda (x)
		  (lambda (y)
		    ((lambda (a)
		       (lambda (b)
			 (lambda (c)
			   (((((a b) c) w) x) y))))
		     (lambda (w)
		       (lambda (x)
			 (lambda (y)
			   (((((a b) c) w) x) y)))))))))))
       '())
(lex '(lambda (a)
          (lambda (b)
	    (lambda (c)
	      (lambda (w)
	        (lambda (x)
		  (lambda (y)
		    ((lambda (a)
		       (lambda (b)
			 (lambda (c)
			   (((((a b) c) w) x) y))))
		     (lambda (w)
		       (lambda (x)
			 (lambda (y)
			   (((((a b) c) w) h) y))))))))))) 
       '())



; Part 2:
; Definition of value-of
(define value-of
  (λ (e env)
    (match e
      [`,n #:when (number? n) n]
      [`,b #:when (boolean? b) b]
      [`(+ ,nexpr1 ,nexpr2) (+ (value-of nexpr1 env) (value-of nexpr2 env))]
      [`(* , r1, r2) (* (value-of r1 env) (value-of r2 env))]
      [`(zero?,v) (=(value-of v env) 0)] 
      [`(sub1, s) (- (value-of s env) 1)]
      [`(if, condition,yes,no)(if(value-of condition env)
                                 (value-of  yes env)
                                 (value-of no env))]
      [`(let ((,x ,y)) ,body)
       (let ([z (value-of y env)])
         (value-of body (lambda (v)
                          (if (eqv? x v)
                               z
                               (env v)))))]
      [`,y #:when(symbol? y) (env y)]
      [`(lambda (,x) ,body)
       (lambda (z)
         (value-of body (lambda (v)
                           (if (eqv? x v)
                                z
                                (env v)))))]
      [`(,rator ,rand)
       ((value-of rator env)
        (value-of rand env))]))) 
      

; Definition of value-of-fn 
(define value-of-fn
  (λ (e env)
    (match e
      [`,n #:when (number? n) n]
      [`,b #:when (boolean? b) b]
      [`(+ ,nexpr1 ,nexpr2) (+ (value-of-fn nexpr1 env) (value-of-fn nexpr2 env))]
      [`(* , r1, r2) (* (value-of-fn r1 env) (value-of-fn r2 env))]
      [`(zero?,v) (=(value-of-fn v env) 0)] 
      [`(sub1, s) (- (value-of-fn s env) 1)]
      [`(if, condition,yes,no)(if(value-of-fn condition env)
                                 (value-of-fn yes env)
                                 (value-of-fn no env))]
      [`(let ((,x ,y)) ,body)
       (let ([z (value-of-fn y env)])
         (value-of-fn body (extend-env-fn x z env)))]
      
      [`,y #:when(symbol? y) (apply-env-fn env y)]
      [`(lambda (,x) ,body)
       #:when (symbol? x)
       (make-clos x env body)]
      [`(,rator ,rand)
       (apply-clos (value-of-fn rator env) (value-of-fn rand env))])))  


; Helper functions from lecture
(define apply-clos
  (λ (rator rand)
    (match rator
      [`(make-clos ,x ,env ,body)
       (value-of-fn body (extend-env-fn x rand env))])))

(define make-clos
  (λ (x env body)
    `(make-clos ,x ,env ,body)))

(define apply-env-fn
  (λ (env y)
    (match env
      [`(extend-env-fn ,x ,arg ,env)
       (cond
        [(eqv? y x) arg]
        [else (apply-env-fn env y)])]
      [`(empty-env-fn)
       (error "Can't find val of " y)])))

(define extend-env-fn
  (λ (x arg env)
    `(extend-env-fn ,x ,arg ,env)))

(define empty-env-fn
  (λ ()
    `(empty-env-fn)))

;Test for part2:
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
(display"\n\nTest for value-of")

(value-of
   '((lambda (x) (if (zero? x)
                     #t
                     #f))
     0)
   (lambda (y) (error 'value-of "unbound variable ~s" y)))

(value-of 
   '((lambda (x) (if (zero? x) 
                     12 
                     47)) 
     0) 
   (lambda (y) (error 'value-of "unbound variable ~s" y)))

(value-of
   '(let ([y (* 3 4)])
      ((lambda (x) (* x y)) (sub1 6)))
   (lambda (y) (error 'value-of "unbound variable ~s" y)))

(value-of
   '(let ([x (* 2 3)])
      (let ([y (sub1 x)])
        (* x y)))
   (lambda (y) (error 'value-of "unbound variable ~s" y)))

(value-of
   '(let ([x (* 2 3)])
      (let ([x (sub1 x)])
        (* x x)))
   (lambda (y) (error 'value-of "unbound variable ~s" y)))

(value-of 
   '(let ((! (lambda (x) (* x x))))
      (let ((! (lambda (n)
                 (if (zero? n) 1 (* n (! (sub1 n)))))))
        (! 5)))
   (lambda (y) (error 'value-of "unbound variable ~s" y)))

(value-of
   '(((lambda (f)
        (lambda (n) (if (zero? n) 1 (* n ((f f) (sub1 n))))))
      (lambda (f)
        (lambda (n) (if (zero? n) 1 (* n ((f f) (sub1 n)))))))
     5)
   (lambda (y) (error 'value-of "unbound variable ~s" y)))

(display"\n \nTest for value-of-fn")

(value-of-fn
   '((lambda (x) (if (zero? x)
                     #t
                     #f))
     0)
   (empty-env-fn))

(value-of-fn 
   '((lambda (x) (if (zero? x) 
                     12 
                     47)) 
     0) 
   (empty-env-fn))

(value-of-fn
   '(let ([y (* 3 4)])
      ((lambda (x) (* x y)) (sub1 6)))
   (empty-env-fn))

(value-of-fn
   '(let ([x (* 2 3)])
      (let ([y (sub1 x)])
        (* x y)))
   (empty-env-fn))

(value-of-fn
   '(let ([x (* 2 3)])
      (let ([x (sub1 x)])
        (* x x)))
   (empty-env-fn))

(value-of-fn
   '(((lambda (f)
        (lambda (n) (if (zero? n) 1 (* n ((f f) (sub1 n))))))
      (lambda (f)
        (lambda (n) (if (zero? n) 1 (* n ((f f) (sub1 n)))))))
     5)
   (empty-env-fn))




;Brain Tesaser:
(define value-of-bt
  (λ (e env)
    (match e
      [`,n #:when (number? n) n]
      [`,b #:when (boolean? b) b]
      [`(+ ,nexpr1 ,nexpr2) (+ (value-of-bt nexpr1 env) (value-of-bt nexpr2 env))]
      [`(* , r1, r2) (* (value-of-bt r1 env) (value-of-bt r2 env))]
      [`(zero?,v) (=(value-of-bt v env) 0)] 
      [`(sub1, s) (- (value-of-bt s env) 1)]
      [`(if, condition,yes,no)(if(value-of-bt condition env)
                                 (value-of-bt  yes env)
                                 (value-of-bt no env))]
      [`(let ((,x ,y)) ,body)
       (let* ([z (value-of-bt y env)] 
              [bx (box z)])    
         (value-of-bt body (lambda (v)
                          (if (eqv? x v) 
                                bx
                               (env v)))))]
      [`(set!, a, b) (set-box!  (env a) (value-of-bt b env))] 
      [`(begin2, e1, e2) (begin (value-of-bt e1 env)) (value-of-bt e2 env)] 
      [`,y #:when(symbol? y) (if (box? (env y))
                                (unbox(env y))
                                (env y))]
      
      [`(lambda (,x) ,body)
         (lambda (z)
           (let([box (box z)])
         (value-of-bt body (lambda (v)
                            (if (eqv? x v)
                                 box 
                                 (env v))))))]
      [`(,rator ,rand)
       ((value-of-bt rator env) 
        (value-of-bt rand env))])))

;; Brain Teaser 5:

(define value-of-lex
  (lambda (exp env)
    (match exp
      [`(const ,expr) expr]
      [`(mult ,x1 ,x2) (* (value-of-lex x1 env) (value-of-lex x2 env))]
      [`(zero ,x) (zero? (value-of-lex x env))]
      (`(sub1 ,body) (sub1 (value-of-lex body env)))
      (`(if ,t ,c ,a) (if (value-of-lex t env) (value-of-lex c env) (value-of-lex a env)))
      (`(var ,num) (apply-env-lex env num))
      (`(lambda ,body) (lambda (a) (value-of-lex body (extend-env-lex a env))))
      (`(,rator ,rand) ((value-of-lex rator env) (value-of-lex rand env))))))

(define apply-env-lex
  (lambda(env n)
    (list-ref  env n)))

(define extend-env-lex
  (lambda(a env)
    (cons a env)))
    
 
(define empty-env-lex 
  (lambda ()
    (lambda (y) (error 'value-of "unbound variable ~s" y))))



;Test for brainteaser
(display "\n\nTest for brainteaser\n")
(value-of-bt
    '(* (begin2 1 1) 3)
    (lambda (y) (error 'value-of-bt "unbound variable ~s" y)))

(value-of-bt
    '((lambda (a)
        ((lambda (p)
           (begin2
             (p a)
             a))
	 (lambda (x) (set! x 4))))
      3)
     (lambda (y) (error 'value-of-bt "unbound variable ~s" y)))


(value-of-bt
   '((lambda (f)
       ((lambda (g)
          ((lambda (z) (begin2
                        (g z)
                        z))
           55))
        (lambda (y) (f y)))) (lambda (x) (set! x 44)))
   (lambda (y) (error 'value-of-bt "unbound variable ~s" y)))


(value-of-bt
    '((lambda (x)
        (begin2 (set! x 5) x))
      6)
    (lambda (y) (error 'value-of-bt "unbound variable ~s" y)))

(value-of-bt 
    '(let ((a 3)) 
        (begin2 (begin2 a (set! a 4)) a))
    (lambda (y) (error 'value-of-bt "unbound variable ~s" y)))

(value-of-bt 
    '((lambda (x)
        (begin2
          ((lambda (y)
	     (begin2
	       (set! x 0)
	       98))
           99)
          x))
      97)
    (lambda (y) (error 'value-of-bt "unbound variable ~s" y)))

(value-of-bt 
    '((lambda (y)
        (let ((x (begin2
                   (set! y 7)
                   8)))
          (begin2
            (set! y 3)
              ((lambda (z) y)
               x))))
      4)
    (lambda (y) (error 'value-of-bt "unbound variable ~s" y)))

(value-of-bt 
    '(let ((a 5))
       (let ((y (begin2 (set! a (sub1 a)) 6)))
         (begin2
           (* y y)
           a)))
    (lambda (y) (error 'value-of-bt "unbound variable ~s" y)))

;;Test for Problem 5:
(display "\n\nTest for problem5(Brainteaser): \n")
(value-of-lex '((lambda (var 0)) (const 5)) (empty-env-lex))

;;Dessert
(define c0 (lambda (f) (lambda (x) x)))
(define c5 (lambda (f) (lambda (x) (f (f (f (f (f x))))))))
(define c+ (lambda (m) 
               (lambda (n) 
                 (lambda (a) (lambda (b) ((m a) ((n a) b)))))))
(define csub1
  (lambda (n)
    (lambda (f)
      (lambda (x)
        (((n (lambda (q) (lambda (w) (w (q f)))))
          (lambda (u) x))
             (lambda (e) e))))))
                        
(((csub1 c5) add1) 0)
(((csub1 c0) add1) 0)