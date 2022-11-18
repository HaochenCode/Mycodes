#lang racket
;Problem 1:
(define list-ref
  (lambda (ls n)
    (letrec
        ([nth-cdr (lambda (n)
	        (cond[(zero? n) ls]
                     [else (cdr(nth-cdr (sub1 n)))]
                    ))])
      (car (nth-cdr n)))))
(display "####Problem 1 Test ####\n")

(list-ref '(a b c) 2)
(list-ref '(a b c) 0)

;Problem 2:
(define union
  (lambda (ls1 ls2)
    (cond[(null? ls1) ls2]
         [(null? ls2) ls1]
         [(memv (car ls1) ls2)(union (cdr ls1) ls2)]
         [else (union (cdr ls1) (cons (car ls1) ls2))])))

(display "####Problem 2 Test ####\n")

(union '() '())
(union '(x) '())
(union '(x) '(x))
(union '(x y) '(x z))

;Problem 3:
(define stretch
  (lambda(pred x)
    (lambda(y)
      (or(eqv? x y) (pred y)))))

(display "####Problem 3 Test ####\n")

((stretch even? 1) 0)
((stretch even? 1) 1)
((stretch even? 1) 2)
((stretch even? 1) 3)
(filter (stretch even? 1) '(0 1 2 3 4 5))
(filter (stretch (stretch even? 1) 3) '(0 1 2 3 4 5))
(filter (stretch (stretch (stretch even? 1) 3) 7) '(0 1 2 3 4 5))

;Problem 4:
(define walk-symbol
  (lambda (x ls)
    (cond[(eqv? (assv x ls) #f) x]
         [(symbol? (cdr(assv x ls)))(walk-symbol  (cdr(assv x ls))ls)] 
         [else (cdr(assv x ls))])))
         
(display "####Problem 4 Test ####\n")

(walk-symbol 'a '((a . 5)))
(walk-symbol 'a '((b . c) (a . b)))
(walk-symbol 'a '((a . 5) (b . 6) (c . a)))
(walk-symbol 'c '((a . 5) (b . (a . c)) (c . a)))
(walk-symbol 'b '((a . 5) (b . ((c . a))) (c . a)))
(walk-symbol 'd '((a . 5) (b . (1 2)) (c . a) (e . c) (d . e)))
(walk-symbol 'd '((a . 5) (b . 6) (c . f) (e . c) (d . e)))



;Problem 5:
(define lambda-exp?
  (λ (E)
    (letrec
      ([p
        (λ (e)
          (match e
            [`,y #:when (symbol? y) #t]
            [`(lambda (,x) ,body)#:when (symbol? x) (p body)]
            [`(,rator ,rand . ,more) (cond[(not(null? more)) #f]
                                          [else (and (p rator) (p rand))])]
            [else #f]))])
      (p E))))

(display "####Problem 5 Test ####\n")
(lambda-exp? 'x)
(lambda-exp? '(lambda (x) x))
(lambda-exp? '(lambda (f) (lambda (x) (f (x x)))))
(lambda-exp? '(lambda (x) (lambda (y) (y x))))
(lambda-exp? '(lambda (z) ((lambda (y) (a z)) (h (lambda (x) (h a))))))
(lambda-exp? '(lambda (lambda) lambda))
(lambda-exp? '((lambda (lambda) lambda) (lambda (y) y)))
(lambda-exp? '((lambda (x) x) (lambda (x) x)))
(lambda-exp? '((lambda (5) x) (lambda (x) x)))
(lambda-exp? '((lambda (x) x) (lambda (x) x) (lambda (x) x)))
(lambda-exp? '((lambda (lambda (x) x) x)  (lambda (x) x)))


;Problem 6:
(define var-occurs?
  (λ(v e)
    (match e
      [`,y #:when (symbol? y)(eqv? y v)]
      [`(lambda (,x) ,body) #:when (symbol? x)(var-occurs? v body)] 
      [`(,rator,rand) (or (var-occurs? v rator)(var-occurs? v rand))])))


(display "####Problem 6 Test ####\n")
(var-occurs? 'x 'x)
(var-occurs? 'x '(lambda (x) y))
(var-occurs? 'x '(lambda (y) x))
(var-occurs? 'x '((z y) x))

;Problem 7:
(define vars
  (λ (E)
    (letrec
      ([p
        (λ (e)
          (match e
            [`,y #:when (symbol? y) (list y)]
            [`(lambda (,x) ,body) (p body)]
            [`(,rator ,rand) (append (p rator) (p rand))]
            [else '()]))])
      (p E))))

(display "####Problem 7 Test ####\n")

(vars 'x)
(vars '(lambda (x) x))
(vars '((lambda (y) (x x)) (x y)))
(vars '(lambda (z) ((lambda (y) (a z))
                      (h (lambda (x) (h a))))))

;Problem 8:
(define unique-vars
  (λ (E)
    (letrec
      ([p
        (λ (e)
          (match e
            [`,y #:when (symbol? y) (list y)]
            [`(lambda (,x) ,body) (p body)]
            [`(,rator ,rand) (union (p rator) (p rand))]
            [else '()]))])
      (p E))))

(display "####Problem 8 Test ####\n")

(unique-vars '((lambda (y) (x x)) (x y)))
(unique-vars '((lambda (z) (lambda (y) (z y))) x))
(unique-vars '((lambda (a) (a b)) ((lambda (c) (a c)) (b a))))

;Problem 9:
(define var-occurs-free?
  (λ(s e)
    (match e
       [`, y #:when (symbol? y) (eqv? y s)]
       [`(lambda (,x),body)(and(not (eqv? x s))(var-occurs-free? s body))]
       [`(,rator,rand)(or(var-occurs-free? s rand) (var-occurs-free? s rator))]
       [ else #f])))


(display "####Problem 9 Test ####\n")

(var-occurs-free? 'x 'x)
(var-occurs-free? 'x '(lambda (y) y))
(var-occurs-free? 'x '(lambda (x) (x y)))
(var-occurs-free? 'x '(lambda (x) (lambda (x) x)))
(var-occurs-free? 'y '(lambda (x) (x y)))
(var-occurs-free? 'y '((lambda (y) (x y)) (lambda (x) (x y))))
(var-occurs-free? 'x '((lambda (x) (x x)) (x x)))
      

;Problem 10:
(define var-occurs-bound?
  (λ (v e)
    (match e
      [`,y #:when (symbol? y)
       #f]
      [`(lambda (,x) ,body) #:when (symbol? x)
       (or (and (eqv? x v) (var-occurs-free? v body))
           (var-occurs-bound? v body))]
      [`(,rator ,rand)
       (or (var-occurs-bound? v rator)
           (var-occurs-bound? v rand))]
      [else #f])))

(display "####Problem 10 Test ####\n")
(var-occurs-bound? 'x 'x)
(var-occurs-bound? 'x '(lambda (x) x))
(var-occurs-bound? 'y '(lambda (x) x))
(var-occurs-bound? 'x '((lambda (x) (x x)) (x x)))
(var-occurs-bound? 'z '(lambda (y) (lambda (x) (y z))))
(var-occurs-bound? 'z '(lambda (y) (lambda (z) (y z))))
(var-occurs-bound? 'x '(lambda (x) y))
(var-occurs-bound? 'x '(lambda (x) (lambda (x) x)))


;Problem 11:
(define unique-free-vars
  (λ (E)
    (letrec
      ([p
        (λ (e)
          (match e
            [`,y #:when (symbol? y) (cond[(var-occurs-free? y E)(list y)]
                                         [else '()])]
            [`(lambda (,x) ,body) (p body)]
            [`(,rator ,rand) (union (p rator) (p rand))]
            [else '()]))])
      (p E))))

(display "####Problem 11 Test ####\n")

(unique-free-vars 'x)
(unique-free-vars '(lambda (x) (x y)))
(unique-free-vars '((lambda (x) ((x y) e)) (lambda (c) (x (lambda (x) (x (e c)))))))

;Problem 12:
(define unique-bound-vars
  (λ (E)
    (letrec
      ([p
        (λ (e)
          (match e
            [`,y #:when (symbol? y) (cond[(var-occurs-bound? y E)(list y)]
                                         [else '()])]
            [`(lambda (,x) ,body) (p body)]
            [`(,rator ,rand) (union (p rator) (p rand))]
            [else '()]))])
      (p E))))

(display "####Problem 12 Test ####\n")
(unique-bound-vars 'x)
(unique-bound-vars '(lambda (x) y))
(unique-bound-vars '(lambda (x) (x y)))
(unique-bound-vars '((lambda (x) ((x y) e)) (lambda (c) (x (lambda (x) (x (e c)))))))
(unique-bound-vars '(lambda (y) y))
(unique-bound-vars '(lambda (x) (y z)))
(unique-bound-vars '(lambda (x) (lambda (x) x)))


;15 Dessert:
(define var-occurs-both?
  (λ(x e)
    (match-let(
         [first (var-occurs-free? x e)] 
         [second (var-occurs-bound? x e)])
      (cons first second))))

(display "####Problem 15 Test ####\n")
(var-occurs-both? 'x '(lambda (x) (x (lambda (x) x))))
(var-occurs-both? 'x '(x (lambda (x) x)))
(var-occurs-both? 'x '(lambda (y) (x (lambda (x) x))))
(var-occurs-both? 'x '(lambda (x) (lambda (x) (x (lambda (x) x)))))
(var-occurs-both? 'x '(lambda (x) (lambda (y) (lambda (x) (x (lambda (x) x))))))
(var-occurs-both? 'x '(lambda (y) (lambda (x) (lambda (z) (lambda (x) (x (lambda (x) x)))))))