#lang racket
; a6: Mr.Sun

;Problem 1:
(define empty-k
  (lambda ()
    (let ((once-only #f))
      (lambda (v)
        (if
         once-only
         (error 'empty-k "You can only invoke the empty continuation once")
         (begin (set! once-only #t) v))))))


(define binary-to-decimal
  (lambda (n)
    (cond
      [(null? n) 0]
      [else (+ (car n) (* 2 (binary-to-decimal (cdr n))))])))

(define binary-to-decimal-cps
  (lambda(n k)
    (cond
      [(null? n) (k 0)]
      [else (binary-to-decimal-cps (cdr n) (lambda(v) (k (+ (* v 2) (car n)))))])))

(display "Test for Problem1:\n")
(binary-to-decimal-cps '() (empty-k))
(binary-to-decimal-cps '(1) (empty-k))
(binary-to-decimal-cps '(0 1) (empty-k))
(binary-to-decimal-cps '(1 1 0 1) (empty-k))


;Problem 2:
(define star
  (lambda (m)
    (lambda (n)
      (* m n))))

(define star-cps
  (lambda (m k)
    (k (lambda (n k^)
          (k^ (* m n))))))

(display "Test for Problem 2:\n")
((star-cps 2 (empty-k)) 3 (empty-k))
((star-cps ((star-cps 2 (empty-k)) 3 (empty-k)) (empty-k)) 5 (empty-k))


;Problem 3:
(define times
  (lambda (ls)
    (cond
      [(null? ls) 1]
      [(zero? (car ls)) 0]
      [else (* (car ls) (times (cdr ls)))])))

(define times-cps
  (lambda (ls k)
    (cond
      [(null? ls) (k 1)]
      [(zero? (car ls)) (k 0)]
      [ else (times-cps (cdr ls)
                         (lambda(v)
                          (k (* (car ls) v))))])))

(display "Test for Problem3: \n")
(times-cps '(1 2 3 4 5) (empty-k))
(times-cps '(1 2 3 0 3) (empty-k))
                
;Problem 4:
(define times-cps-shortcut
  (lambda (ls k)
    (cond
      [(null? ls) (k 1)]
      [(zero? (car ls)) 0]
      [ else (times-cps-shortcut (cdr ls)
                         (lambda(v)
                          (k (* (car ls) v))))])))

(display "Test for Problem4: \n")
(times-cps-shortcut '(1 2 3 4 5) (empty-k))
(times-cps-shortcut '(1 2 3 0 3) (empty-k))

;Problem 5:
(define remv-first-9*
  (lambda (ls)
    (cond
      [(null? ls) '()]
      [(pair? (car ls))
       (cond
         [(equal? (car ls) (remv-first-9* (car ls)))
          (cons (car ls) (remv-first-9* (cdr ls)))]
         [else (cons (remv-first-9* (car ls)) (cdr ls))])]
      [(eqv? (car ls) '9) (cdr ls)]
      [else (cons (car ls) (remv-first-9* (cdr ls)))])))

(define remv-first-9*-cps
  (lambda (ls k)
    (cond
      [(null? ls) (k '())]
      [(pair? (car ls))
       (remv-first-9*-cps
        (car ls)
        (lambda (v)
          (cond
            [(equal? (car ls) v)
             (remv-first-9*-cps (cdr ls)
                                (lambda (v2)
                                  (k (cons (car ls) v2))))]
            [else (k (cons v (cdr ls)))])))]
      [(eqv? (car ls) '9) (k (cdr ls))]
      [else
       (remv-first-9*-cps
        (cdr ls)
        (lambda (v)
          (k (cons (car ls) v))))])))

(display "Test for Problem5: \n")
(remv-first-9*-cps '((1 2 (3) 9)) (empty-k))
(remv-first-9*-cps '(9 (9 (9 (9)))) (empty-k))
(remv-first-9*-cps '(((((9) 9) 9) 9) 9) (empty-k))

;Problem 6:
(define cons-cell-count
  (lambda (ls)
    (cond
      [(pair? ls)
       (add1 (+ (cons-cell-count (car ls))
                (cons-cell-count (cdr ls))))]
      [else 0])))

(define cons-cell-count-cps
  (lambda (ls k)
    (cond
      [(pair? ls)
       (cons-cell-count-cps
        (car ls)
        (lambda (v1)
          (cons-cell-count-cps
           (cdr ls)
           (lambda (v2)
             (k (add1 (+ v1 v2)))))))
       ]
      [else (k 0)])))

(display "Test for Problem6: \n")
(cons-cell-count-cps '(1 2 3 4) (empty-k))
(cons-cell-count-cps '(1 2 (3 (4) 5) 4 ()) (empty-k))

;Problem 7:
(define find 
  (lambda (u s)
    (let ((pr (assv u s)))
      (if pr (find (cdr pr) s) u))))

(define find-cps
  (lambda (u s k)
    (let ((pr (assv u s)))
      (if pr
         (find-cps (cdr pr) s k)
         (k u)))))

(display "Test for Problem7: \n")
(find-cps 5 '((5 . a) (6 . b) (7 . c)) (empty-k))
(find-cps 7 '((5 . a) (6 . 5) (7 . 6)) (empty-k))
(find-cps 5 '((5 . 6) (9 . 6) (2 . 9)) (empty-k))

;Problem 8:
(define ack
  (lambda (m n)
    (cond
      [(zero? m) (add1 n)]
      [(zero? n) (ack (sub1 m) 1)]
      [else (ack (sub1 m)
                 (ack m (sub1 n)))])))

(define ack-cps
  (lambda (m n k)
    (cond
      [(zero? m)
       (k (add1 n))]
      [(zero? n)
       (ack-cps (sub1 m) 1 k)]
      [else (ack-cps m
                 (sub1 n)
                 (lambda (v) (ack-cps (sub1 m) v k)))])))


;Problem 9:
(define fib
  (lambda (n)
    ((lambda (fib)
       (fib fib n))
     (lambda (fib n)
       (cond
        [(zero? n) 0]
        [(zero? (sub1 n)) 1]
        [else (+ (fib fib (sub1 n)) (fib fib (sub1 (sub1 n))))])))))

(define fib-cps
  (lambda (n k)
    ((lambda (fib-cps k)
       (fib-cps fib-cps n k))
     (lambda (fib-cps n k)
       (cond
         [(zero? n) (k n)]
         [(= 1 n) (k 1)]
         [else
          (fib-cps fib-cps
                   (sub1 (sub1 n))
                   (lambda(x) (fib-cps fib-cps
                                       (sub1 n)
                                       (lambda (y) (k (+ x y))))))])) k)))

;Problem 10:
(define unfold
  (lambda (p f g seed)
    ((lambda (h)
       ((h h) seed '()))
     (lambda (h)
       (lambda (seed ans)
        (if (p seed)
            ans
            ((h h) (g seed) (cons (f seed) ans))))))))

(define null?-cps
    (lambda (ls k)
      (k (null? ls))))

(define car-cps
    (lambda (pr k)
      (k (car pr))))

(define cdr-cps
    (lambda (pr k)
      (k (cdr pr))))

(define unfold-cps
  (lambda (p f g seed k)
    ((lambda (h1 k)
       (h1 h1
              (lambda (v) (v seed '() k))))
     (lambda (h2 k)
       (k (lambda (seed ans k)
            (p seed (lambda (x)
                      (if x
                          (k ans)
                          (h2 h2
                                 (lambda (y)
                                   (f seed (lambda (z)
                                             (g seed (lambda (a)
                                                       (y a (cons z ans) k))))))))))))) k)))

(display"Test for Problem 10:\n")
(unfold-cps null?-cps car-cps cdr-cps '(a b c d e) (empty-k))

;Problem 11:
(define empty-s
  (lambda ()
    '()))

(define extend-s
  (lambda (x v s)
    `((,x . ,v) . ,s)))

(define unify
  (lambda (u v s)
    (cond
      ((eqv? u v) s)
      ((number? u) (cons (cons u v) s))
      ((number? v) (unify v u s))
      ((pair? u)
       (if
        (pair? v)
        (let ((s (unify (find (car u) s) (find (car v) s) s)))
             (if s (unify (find (cdr u) s) (find (cdr v) s) s) #f))
        #f))
      (else #f))))

(define unify-cps
  (lambda (v w s k)
    (find-cps v s 
              (lambda (v)
                (find-cps w s
                          (lambda (w)
                            (cond
                              [(eqv? v w) (k s)]
                              [(symbol? v) (k (extend-s v w s))]
                              [(symbol? w) (k (extend-s w v s))]
                              [(and (pair? v) (pair? w))
                               (unify-cps (car v) (car w) s
                                          (lambda (s^)
                                            (if s^
                                                (unify-cps (cdr v) (cdr w) s^ 
                                                           (lambda (s^^)
                                                             (k s^^)))#f)))]
                              [(equal? v w) (k s)]
                              [else (k #f)])))))))

(display "Tests for Problem11: \n")
(unify 'x 5 (empty-s))
(unify 'x 5 (unify 'y 6 (empty-s)))
(unify '(x y) '(5 6) (empty-s))
(unify 'x 5 (unify 'x 6 (empty-s)))
(unify '(x x) '(5 6) (empty-s))
(unify '(1 2 3) '(x 1 2) (empty-s))
(unify 'x 'y (empty-s))
(unify-cps '(a b c) '(2 a b) (empty-s) (empty-k))


;Problem 12:
(define M
  (lambda (f)
    (lambda (ls)
      (cond
        ((null? ls) '())
        (else (cons (f (car ls)) ((M f) (cdr ls))))))))

(define M-cps
  (lambda (f k)
    (k (lambda (ls k)
         (cond
           [(null? ls)
            (k '())]
           [else 
            (M-cps f
                   (lambda (x)
                     (x (cdr ls) (lambda (y)
                                   (f (car ls) (lambda (z)
                                                 (k (cons z y))))))))])))))

(display "Tests for Problem12: \n")
((M-cps (lambda (a k) (k (add1 a))) (empty-k)) '(1 2 3) (empty-k))
;Problem 13:
(define use-of-M
  ((M (lambda (n) (add1 n))) '(1 2 3 4 5)))

(define use-of-M-cps
  ((M-cps (lambda (n k) (k (add1 n))) (empty-k)) '(1 2 3 4 5) (empty-k)))


;BTs

;Problem 14:
(define strange
  (lambda (x)
    ((lambda (g) (lambda (x) (g g)))
     (lambda (g) (lambda (x) (g g))))))

(define stange-cps
  (lambda(x k)
    ((lambda(y k)
       (k(lambda (x k) (y y k))))
     (lambda (z k)
       (k(lambda(x k) (z z k))))
     k)))


;Problem 15:
(define use-of-strange
  (let ([strange^ (((strange 5) 6) 7)])
    (((strange^ 8) 9) 10)))

(define use-of-strange-cps
  (let([strange^(((strange-cps 5 (empty-k)) 6 (empty-k)) 7 (empty-k))])
    (((strange^ 8 ((empty-k)) 9 (empty-k)) 10 (empty-k)))))


;Problem 16:
(define why
  (lambda (f)
    ((lambda (g)
       (f (lambda (x) ((g g) x))))
     (lambda (g)
       (f (lambda (x) ((g g) x)))))))

(define almost-length
    (lambda (f)
      (lambda (ls)
        (if (null? ls)
            0
            (add1 (f (cdr ls)))))))

;(define why-cps
;  (lambda(f k)
;    (lambda (g k)
;      (f (lambda (h k) (g g (lambda g^ h)))) k))
;    NO IDEA..