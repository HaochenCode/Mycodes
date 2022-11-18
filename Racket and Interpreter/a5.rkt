#lang racket
;Part 1:
;Value-of interpreter
(define value-of
  (lambda (exp env)
    (match exp
      [`,b #:when (boolean? b) b]
      [`,n #:when (number? n)  n]
      [`(zero? ,n)
        (zero? (value-of n env))]
      [`(sub1 ,n)
        (sub1 (value-of n env))]
      [`(* ,n1 ,n2)
        (* (value-of n1 env)
           (value-of n2 env))]
      [`(if ,test ,conseq ,alt)
        (if (value-of test env)
            (value-of conseq env)
            (value-of alt env))]
      [`(begin2 ,e1 ,e2)
        (begin (value-of e1 env)
               (value-of e2 env))]
      [`(random ,n)
        (random (value-of n env))]
      [`,y #:when (symbol? y)
        (apply-env env y)]
      [`(lambda (,x) ,body)
        (make-closure x body env)]
      [`(,rator ,rand)
        (apply-closure
         (value-of rator env)
         (value-of rand env))])))

;closure-cbv:
(define closure-cbv
  (lambda (y body env)
    (lambda (c)
      (val-of-cbv body (extend-env y c env)))))

;apply-closure
(define apply-closure
  (lambda (a b)
    (a b)))

;make-closure
(define make-closure
  (lambda (y body env)
    (lambda (x)
      (value-of body (extend-env y x env)))))

;apply-env
(define apply-env
  (lambda (env n)
    (match env
      (`(empty-env) `empty-env)
      (`(extend-env ,x ,y ,env) (if (eqv? x n) y (apply-env env n))))))

;extend-env
(define extend-env
  (lambda (x y env)
    `(extend-env ,x ,y ,env)))

;empty-env
(define empty-env
  (λ ()
    (λ (y)
      (error "Free variable found" y))))

; val-of-cbv interpreter:
(define val-of-cbv
  (lambda (exp env) 
    (match exp
      [`,y #:when (symbol? y)
           (unbox (apply-env env y))]
      [`,y #:when (number? y)
           y]
      [`,y #:when (boolean? y)
           y]
      [`(zero? ,n)
       (= (val-of-cbv n env) 0)]
      [`(sub1 ,x)
       (- (val-of-cbv x env) 1)]
      [`(* ,x ,y)
       (* (val-of-cbv x env)
          (val-of-cbv y env))]
      [`(lambda (,x) ,body)
       (closure-cbv x body env)]
      [`(if ,cond true false)
       (if (val-of-cbv cond env)
           (val-of-cbv true env)
           (val-of-cbv false env))]
      [`(random ,n)
       (random (val-of-cbv n env))]
      [`(begin2 ,e1 ,e2)
       (begin (val-of-cbv e1 env)
              (val-of-cbv e2 env))]
      [`(set! ,x ,body)
       (set-box! (apply-env env x)
                 (val-of-cbv body env))]
      [`(,rator ,rand) #:when (symbol? rand)
                       (apply-closure (val-of-cbv rator env)
                                      (box (apply-env env rand)))]
      [`(,rator ,rand)
       (apply-closure (val-of-cbv rator env)
                      (box (val-of-cbv rand env)))])))

;cbr closure:
(define closure-cbr
  (lambda (y body env)
    (lambda (x)
      (val-of-cbr body (extend-env y x env)))))

;val-of-cbr interpreter:
(define val-of-cbr
  (lambda (exp env)
    (match exp
      [`,y #:when (symbol? y)
           (unbox (apply-env env y))]
      [`,y #:when (number? y)
           y]
      [`,y #:when (boolean? y)
           y]
      [`(zero? ,n)
       (= (val-of-cbr n env) 0)]
      [`(sub1 ,x)
       (- (val-of-cbr x env) 1)]
      [`(* ,x ,y)
       (* (val-of-cbr x env)
          (val-of-cbr y env))]
      [`(lambda (,x) ,body)
       (closure-cbr x body env)]
      [`(if ,cond ,true ,false)
       (if (val-of-cbr cond env)
           (val-of-cbr true env)
           (val-of-cbr false env))]
      [`(random ,n)
       (random (val-of-cbr n env))]
      [`(begin2 ,e1 ,e2)
       (begin (val-of-cbr e1 env)
              (val-of-cbr e2 env))]
      [`(set! ,x ,body)
       (set-box! (apply-env env x)
                 (val-of-cbr body env))]
      [`(,rator ,rand) #:when (symbol? rand)
                       (apply-closure (val-of-cbr rator env)
                                      (apply-env env rand))]
      [`(,rator ,rand)
       (apply-closure (val-of-cbr rator env)
                      (box (val-of-cbr rand env)))])))

;cbname closure:
(define closure-cbname
  (lambda (y body env)
    (lambda (x)
      (val-of-cbname body (extend-env y x env)))))

;val-of-cbname interpreter:
(define val-of-cbname
  (lambda (exp env)
    (match exp
      [`,y #:when (symbol? y)
           ((unbox (apply-env env y)))]
      [`,y #:when (number? y)
           y]
      [`,y #:when (boolean? y)
           y]
      [`(zero? ,n)
       (= (val-of-cbname n env) 0)]
      [`(sub1 ,x)
       (- (val-of-cbname x env) 1)]
      [`(* ,x ,y)
       (* (val-of-cbname x env)
          (val-of-cbname y env))]
      [`(lambda (,x) ,body)
       (closure-cbname x body env)]
      [`(if ,cond ,true ,false)
       (if (val-of-cbname cond env)
           (val-of-cbname true env)
           (val-of-cbname false env))]
      [`(random ,n)
       (random (val-of-cbname n env))]
      [`(begin2 ,e1 ,e2)
       (begin (val-of-cbname e1 env)
              (val-of-cbname e2 env))]
      [`(set! ,x ,body)
       (set-box! (val-of-cbname env x)
                 (val-of-cbname body env))]
      [`(,rator ,rand) #:when (symbol? rand)
                       (apply-closure (val-of-cbname rator env)
                                      (apply-env env rand))]
      [`(,rator ,rand)
       (apply-closure (val-of-cbname rator env)
                      (box (lambda () (val-of-cbname rand env))))])))

;cbneed closure:
(define closure-cbneed
  (lambda (y body env)
    (lambda (c)
      (val-of-cbneed body (extend-env y c env)))))

;val-of-cbneed interpreter:
(define val-of-cbneed
  (lambda (exp env)
    (match exp
      [`,y #:when (symbol? y)
           (let ([b (apply-env env y)])
             (let ([val ((unbox b))])
               (set-box! b (lambda () val))
               val))]
      [`,y #:when (number? y)
           y]
      [`,y #:when (boolean? y)
           y]
      [`(zero? ,n)
       (= (val-of-cbneed n env) 0)]
      [`(sub1 ,x)
       (- (val-of-cbneed x env) 1)]
      [`(* ,x ,y)
       (* (val-of-cbneed x env)
          (val-of-cbneed y env))]
      [`(lambda (,x) ,body)
       (closure-cbneed x body env)]
      [`(if ,cond ,true ,false)
       (if (val-of-cbneed cond env)
           (val-of-cbneed true env)
           (val-of-cbneed false env))]
      ;;random
      [`(random ,n)
       (random (val-of-cbneed n env))]
      ;;begin
      [`(begin2 ,e1 ,e2)
       (begin (val-of-cbneed e1 env)
              (val-of-cbneed e2 env))]
      ;;set!
      [`(set! ,x ,body)
       (set-box! (val-of-cbneed env x)
                 (val-of-cbneed body env))]
      ;;rand -> symbol
      [`(,rator ,rand) #:when (symbol? rand)
                       (apply-closure (val-of-cbneed rator env)
                                      (apply-env env rand))]
      ;;normal rator, rand
      [`(,rator ,rand)
       (apply-closure (val-of-cbneed rator env)
                      (box (lambda () (val-of-cbneed rand env))))])))

(display "Tests for part 1: \n")
;Tests:
(val-of-cbr
   '((lambda (x) (begin2 (set! x #t)
                         (if x 3 5))) #f)
   (empty-env))

(val-of-cbr
   '((lambda (a)
       ((lambda (p)
          (begin2
           (p a)
           a)) (lambda (x) (set! x 4)))) 3)
   (empty-env))

(val-of-cbv
   '((lambda (a)
       ((lambda (p)
          (begin2
           (p a)
           a)) (lambda (x) (set! x 4)))) 3)
   (empty-env))


(val-of-cbr
   '((lambda (f)
       ((lambda (g)
          ((lambda (z) (begin2
                        (g z)
                        z))
           55))
        (lambda (y) (f y)))) (lambda (x) (set! x 44)))
   (empty-env))

(val-of-cbv
   '((lambda (f)
       ((lambda (g)
          ((lambda (z) (begin2
                        (g z)
                        z))
           55))
        (lambda (y) (f y)))) (lambda (x) (set! x 44)))
   (empty-env))

(val-of-cbr
   '((lambda (swap)
       ((lambda (a)
          ((lambda (b)
             (begin2
              ((swap a) b)
              a)) 44)) 33))
     (lambda (x)
       (lambda (y)
         ((lambda (temp)
            (begin2
             (set! x y)
             (set! y temp))) x))))
   (empty-env))

(val-of-cbv
   '((lambda (swap)
       ((lambda (a)
          ((lambda (b)
             (begin2
              ((swap a) b)
              a)) 44)) 33))
     (lambda (x)
       (lambda (y)
         ((lambda (temp)
            (begin2
             (set! x y)
             (set! y temp))) x))))
   (empty-env))

(define random-sieve
    '((lambda (n)
        (if (zero? n)
            (if (zero? n) (if (zero? n) (if (zero? n) (if (zero? n) (if (zero? n) (if (zero? n) #t #f) #f) #f) #f) #f) #f)
            (if (zero? n) #f (if (zero? n) #f (if (zero? n) #f (if (zero? n) #f (if (zero? n) #f (if (zero? n) #f #t))))))))
      (random 2)))
(val-of-cbname random-sieve (empty-env))
(val-of-cbneed random-sieve (empty-env))

(val-of-cbname
   '((lambda (z) 100)
     ((lambda (x) (x x)) (lambda (x) (x x))))
   (empty-env))

;Part2:
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
      [`(letrec ,1/2-closure ,b)
       (value-of-ds b (ext-rec-env 1/2-closure env))]
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
      [`(rec-env ,1/2-closure ,env^)
       (cond
         [(assv y 1/2-closure)
          => (λ (p)
               (let ([val-e (cadr p)])
                 (value-of-ds val-e env)))])]
      [`(empty-env-ds)
       (error "Can't find val of " y)]))) 

(define extend-env-ds
  (lambda (x arg env)
    `(extend-env-ds ,x ,arg ,env)))

(define ext-rec-env
  (λ (1/2-closure env)
    `(rec-env ,1/2-closure ,env)))

(define empty-env-ds
  (lambda ()
    `(empty-env-ds)))

(display "Test for part 2: \n")
(value-of-ds '(letrec ([x 22]
                         [y 20]
                         [z (+ x y)])
                 z)
               (empty-env-ds))

(value-of-ds '(letrec ([even? (lambda (n)
                                  (if (zero? n)
                                      #t
                                      (odd? (sub1 n))))]
                         [odd? (lambda (n)
                                 (if (zero? n)
                                     #f
                                     (even? (sub1 n))))])
                  (even? 11))
               (empty-env-ds))

(value-of-ds '(letrec ([even? (lambda (n)
                                  (if (zero? n)
                                      #t
                                      (odd? (sub1 n))))]
                         [odd? (lambda (n)
                                 (if (zero? n)
                                     #f
                                     (even? (sub1 n))))])
                  (even? 42))
               (empty-env-ds))

(display "Test for BT:\n")
; Brain Teasers:
(define val-of-cbv-bt
  (lambda (exp env) 
    (match exp
      [`,y #:when (symbol? y)
           (unbox (apply-env env y))]
      [`,y #:when (number? y)
           y]
      [`,y #:when (boolean? y)
           y]
      [`(zero? ,n)
       (= (val-of-cbv-bt n env) 0)]
      [`(null?, ls) ls]
      [`(sub1 ,x)
       (- (val-of-cbv-bt x env) 1)]
      [`(add1 ,x)
       (+ (val-of-cbv-bt x env) 1)]
      [`(car ,ls) (car ls)]
      [`(car^ ,ls) ((car (val-of-cbv-bt ls exp)))]
      [`(cons ,x ,ls) (cons x ls)]
      [`(cons^ ,a,ls) (lambda() (val-of-cbv-bt a env))
                      (lambda() (val-of-cbv-bt ls env))]
      [`(cdr, ls) (cdr ls)]
      [`(cdr^,ls) ((cdr(val-of-cbv-bt ls exp)))]
      ['() '()]
      [`(* ,x ,y)
       (* (val-of-cbv-bt x env)
          (val-of-cbv-bt y env))]
      [`(lambda (,x) ,body)
       (closure-cbv x body env)]
      [`(let ([,x , exp1]) ,body)
       (extend-env-ds x exp1 env) (val-of-cbv-bt exp1 body)]
      [`(if ,cond true false)
       (if (val-of-cbv-bt cond env)
           (val-of-cbv-bt true env)
           (val-of-cbv-bt false env))]
      [`(random ,n)
       (random (val-of-cbv-bt n env))]
      [`(begin2 ,e1 ,e2)
       (begin (val-of-cbv-bt e1 env)
              (val-of-cbv-bt e2 env))]
      [`(set! ,x ,body)
       (set-box! (apply-env env x)
                 (val-of-cbv-bt body env))]
      [`(,rator ,rand) #:when (symbol? rand)
                       (apply-closure (val-of-cbv-bt rator env)
                                      (box (apply-env env rand)))]
      [`(,rator ,rand)
       (apply-closure (val-of-cbv-bt rator env)
                      (box (val-of-cbv-bt rand env)))]
      [`,ls #:when (list? ls) ls])))

(define cons-test
    (quote
     (let ((fix (lambda (f)
                  ((lambda (x) (f (lambda (v) ((x x) v))))
                   (lambda (x) (f (lambda (v) ((x x) v))))))))
       (let ((map (fix (lambda (map)
                         (lambda (f)
                           (lambda (l)
                             (if (null? l)
                                 '()
                                 (cons^ (f (car^ l))
                                        ((map f) (cdr^ l))))))))))
         (let ((take (fix (lambda (take)
                            (lambda (l)
                              (lambda (n)
                                (if (zero? n)
                                    '()
                                    (cons (car^ l) 
                                          ((take (cdr^ l)) (sub1 n))))))))))
           ((take ((fix (lambda (m)
                          (lambda (i)
                            (cons^ 1 ((map (lambda (x) (add1 x))) (m i)))))) 0)) 5))))))

(val-of-cbv-bt cons-test (empty-env))