#lang racket
(require "mk.rkt")
(require "numbers.rkt")
(require "monads.rkt")
;Part 1:
;Problem 1:
(defrel (listo ls)
  (conde
     [(== ls empty)]
     [(fresh (x y)
             (== `(,x . ,y) ls)
             (listo y))]))

;Tests
(run! 1 q (listo '(a b c d e)))
;(_0)

(run! 1 q (listo '(a b c d . e)))
;()

(run! 4 q (listo q))
;(() (_0) (_0 _1) (_0 _1 _2))

(run! 4 q (listo `(a b ,q)))
;(_0)


;Problem 2:
(defrel (facto num1 num2)
  (conde
      [(== num1 empty) (== num2 '(1))]
      [(fresh (f1 f2)
              (facto f1 f2)
              (minuso num1 '(1) f1)
              (*o num1 f2 num2))]))

;Tests:
(run! 1 q (facto  q '(0 0 0 1 1 1 1)))
;((1 0 1))

(run! 1 q (facto (build-num 5) q))
;((0 0 0 1 1 1 1))

(run! 6 q (fresh (n1 n2) (facto n1 n2) (== `(,n1 ,n2) q)))
;((() (1)) 
;((1) (1)) 
;((0 1) (0 1)) 
;((1 1) (0 1 1))
;((0 0 1) (0 0 0 1 1)) 
;((1 0 1) (0 0 0 1 1 1 1)))



;Part 2:
;Problem 1:

(define (findf-maybe f ls)
  (match ls
    [`() '(Nothing)]
    [`(,a . ,b) (if (f a) `(Just ,a) (findf-maybe f b))]))

;Tests
(findf-maybe symbol? '(1 2 c))
;(Just 'c)

(findf-maybe boolean? '(#f 1 2 c)) 
;(Just #f)

(findf-maybe number? '(a b c))
;(Nothing)



;Problem 2:

(define (partition-writer f ls)
  (cond
    [(null? ls) (inj-writer ls)]
    ;False case:
    [(not (f (car ls)))
     (bind-writer
      (tell (car ls))
      (lambda (x)
        (partition-writer f (cdr ls))))]
    ;True case:
    [else
     (bind-writer
      (partition-writer f (cdr ls))
      (lambda (x)
(inj-writer (cons (car ls) x))))]))
  

;Tests
(run-writer (partition-writer even? '(1 2 3 4 5 6 7 8 9 10)))
;((2 4 6 8 10) . (1 3 5 7 9))

(run-writer (partition-writer odd? '(1 2 3 4 5 6 7 8 9 10)))
;((1 3 5 7 9) . (2 4 6 8 10))



;Problem 3:

(define power
(lambda (x n)
    (cond
    [(zero? n) 1]
    [(zero? (sub1 n)) x]
    [(odd? n) (* x (power x (sub1 n)))]
    [(even? n) (let ((nhalf (/ n 2)))
                (let ((y (power x nhalf)))
                    (* y y)))])))


(define powerXpartials
  (λ (x n)
    (cond
      [(zero? (sub1 n))
       (go-on
        (tell x)
        (inj-writer x))]
      [else
       (go-on
        (r <- (powerXpartials x (sub1 n)))
        (tell (* x r))
        (inj-writer (* x r)))])))
;Tests
(run-writer (powerXpartials 2 6))
;(64 . (2 4 8))

(run-writer (powerXpartials 3 5))
;(243 . (3 9 81))

(run-writer (powerXpartials 5 7))
;(78125 . (5 25 125 15625))



;Problem 4:

(define replace-with-count
  (λ (x tr)
    (cond
      [(and (pair? tr)
            (eqv? (car tr) x))
       (go-on
        (c <- (get))
        (put (+ c 1))
        (tr^ <- (replace-with-count x (cdr tr)))
        (inj-state (cons c tr^)))]
      [(and (pair? tr)
            (pair? (car tr)))
       (go-on
        (tr^ <- (replace-with-count x (car tr)))
        (tr^^ <- (replace-with-count x (cdr tr)))
        (inj-state
         (cons tr^ tr^^)))]
      [(pair? tr)
       (go-on
        (tr^ <- (replace-with-count x (cdr tr)))
        (inj-state
         (cons (car tr) tr^)))]
      [(and (symbol? tr) (eqv? tr x))
       (go-on
        (c <- (get))
        (put (+ c 1))
        (inj-state c))]
      [else
       (inj-state tr)])))

;Tests:

((run-state (replace-with-count 'o '(a o (t o (e o t ((n . m) . o) . f) . t) . r))) 0)
;((a 0 (t 1 (e 2 t ((n . m) . 3) . f) . t) . r) . 4)

((run-state (replace-with-count 'o '(((h (i s . o) . a) o s o e . n) . m))) 0)
;(((h (i s . 0) . a) 1 s 2 e . n) . m) . 3)

((run-state (replace-with-count 'o '(o (h (o s . o) . o) . o))) 1)
;((1 (h (2 s . 3) . 4) . 5) . 6)


;Problem 5:

(define traverse
  (lambda (inj bind f)
    (letrec
        ((trav
          (lambda (tree)
            (cond
              [(pair? tree)
               (go-on (a <- (trav (car tree)))
                      (d <- (trav (cdr tree)))
                      (inj (cons a d)))]
              [else (f tree)]))))
      trav)))


(define reciprocal
  (λ (n)
    (cond
      [(zero? n) (Nothing)]
      [else (Just (/ 1 n))])))

;Tests:
(reciprocal 0)
;(Nothing)

(reciprocal 2)
;(Just 1/2)

(define traverse-reciprocal
  (traverse Just bind-maybe reciprocal))

(traverse-reciprocal '((1 . 2) . (3 . (4 . 5))))
;(Just ((1 . 1/2) . (1/3 . (1/4 . 1/5))))

(traverse-reciprocal '((1 . 2) . (0 . (4 . 5))))
;(Nothing)



;Problem 6:
(define halve
  (λ (n)
    (cond
      [(zero? (modulo n 2))
       (inj-writer (/ n 2))]
      [else
       (go-on
        (tell n)
        (inj-writer n))])))

;Tests:
(run-writer (halve 6))
;(3 . ())

(run-writer (halve 5))
;(5 . (5))

(define traverse-halve
  (traverse inj-writer bind-writer halve))

(run-writer (traverse-halve '((1 . 2) . (3 . (4 . 5)))))
;(((1 . 1) . (3 . (2 . 5))) . (1 3 5))


;Problem 7:

(define state/sum
  (λ (n)
    (go-on
     (m <- (get))
     (put n)
     (inj-state m))))

((run-state (state/sum 5)) 0)
;(0 . 5)

((run-state (state/sum 2)) 0)
;(0 . 2)

((run-state (state/sum 2)) 3)
;(3 . 5)

;Tests:
(define traverse-state/sum
  (traverse inj-state bind-state state/sum))

((run-state (traverse-state/sum '((1 . 2) . (3 . (4 . 5))))) 0)
;(((0 . 1) 3 6 . 10) . 15)