#lang racket
(require "mk.rkt")

;definitions:
(defrel (∈ Γ x τ)
  (fresh (xa τa restΓ)
    (== `((,xa . ,τa) . ,restΓ) Γ)
    (conde
     [(== x xa)
      (== τa τ)]
     [(=/= x xa)
      (∈ restΓ x τ)])))

(defrel (⊢ Γ e τ)
  (conde
   [(numbero e) (== 'Nat τ)]
   [(conde
     [(== #t e)]
     [(== #f e)])
    (== 'Bool τ)]
   [(fresh (e1 e2)
      (== `(* ,e1 ,e2) e)
      (== 'Nat τ)
      (⊢ Γ e1 'Nat)
      (⊢ Γ e2 'Nat))]
   [(fresh (e1 e2)
      (== `(+ ,e1 ,e2) e)
      (== 'Nat τ)
      (⊢ Γ e1 'Nat)
      (⊢ Γ e2 'Nat))]
   [(fresh (e^)
      (== `(zero? ,e^) e)
      (== 'Bool τ)
      (⊢ Γ e^ 'Nat))]
   [(fresh (x y)
      (== `(and,x,y) e)
      (== 'Bool τ)
      (⊢ Γ x 'Bool)
      (⊢ Γ y 'Bool))]
   [(fresh (e^)
      (== `(sub1 ,e^) e)
      (== 'Nat τ)
      (⊢ Γ e^ 'Nat))]
   [(fresh (x)
           (== `(not,x) e)
           (== 'Bool τ)
           (⊢ Γ x 'Bool))]
   [(fresh (test conseq alt)
      (== `(if ,test ,conseq ,alt) e)
      (⊢ Γ test 'Bool)
      (⊢ Γ conseq τ)
      (⊢ Γ alt τ))]
   [(fresh (f body)
      (symbolo f)
      (== `(fix (λ (,f) ,body)) e)
      (⊢ `((,f . ,τ) . ,Γ) body τ))]
   [(symbolo e)
    (∈ Γ e τ)]
   [(fresh (x body τx τbody)
      (symbolo x)
      (== `(λ (,x) ,body) e)
      (== `(-> ,τx ,τbody) τ)
      (⊢ `((,x . ,τx) . ,Γ) body τbody))]
   [(fresh (rator rand τx)
      (== `(,rator ,rand) e)
      (⊢ Γ rand τx)
      (⊢ Γ rator `(-> ,τx ,τ)))]))


;Tests:
  (run*! q (⊢ '() #t q))
;(Bool)
  (run*! q (⊢ '() 17 q))
;(Nat)
  (run*! q (⊢ '() '(zero? 24) q))
;(Bool)
  (run*! q (⊢ '() '(zero? (sub1 24)) q))
;(Bool)
  (run*! q (⊢ '() '(not (zero? (sub1 24))) q))
;(Bool)
  (run*! q
    (⊢ '() '(zero? (sub1 (sub1 18))) q))
;(Bool)
  (run*! q
    (⊢ '()  '(λ (n) (if (zero? n) n n)) q))
;((Nat  -> Nat))
 (run*! q
    (⊢ '() '(λ (n)
               (λ (b)
                 (if (and (not b) (zero? n))
                     n n))) q))
;((Nat  -> (Bool  -> Nat)))
  (run*! q
    (⊢ '() '((λ (n) (zero? n)) 5) q))
;(Bool)
  (run*! q
    (⊢ '() '(if (zero? 24) 3 4) q))
;(Nat)
  (run*! q
    (⊢ '() '(if (zero? 24) (zero? 3) (zero? 4)) q))
;(Bool)
  (run*! q
    (⊢ '() '(λ (x) (sub1 x)) q))
;((Nat  -> Nat))
 (run*! q (⊢ '() (and (zero? 5) (not #t)) q))
;(Bool)
 (run*! q (⊢ '() (or #f (not #t)) q))
;(Bool)
  (run*! q
    (⊢ '() '(λ (a) (λ (x) (+ a x))) q))
;((Nat  -> (Nat  -> Nat)))
  (run*! q
    (⊢ '() '(λ (f)
               (λ (x)
                 ((f x) x))) 
         q))
;(((_0  -> (_0  -> _1))  -> (_0  -> _1)))
  (run*! q
    (⊢ '() '(sub1 (sub1 (sub1 6))) q))
;(Nat)
  (run 1 q
    (fresh (t)
      (⊢ '() '(λ (f) (f f)) t)))
;() 
  (length (car (run 20 (q)
             (fresh (lam a b)
               (⊢ '() `((,lam (,a) ,b) 5) 'Nat)
               (== `(,lam (,a) ,b) q)))))
;20
  (length (car (run 30 q (⊢ '() q 'Nat))))
;30
  (length (car (run 30 q (⊢ '() q '(Nat  -> Nat)))))
;30
  (length (car (run 500 q (⊢ '() q '(Nat  -> Nat)))))
;500
;; At this point, stop and take a look at maybe the 500th 
;; program you generate
;; (last (car (run 500 q (⊢ '() q '(Nat  -> Nat)))))
;; You should be amazed at how quickly it's generating them.
;; If it isn't fast, consider reordering your clauses. 
  (length (car (run 30 q (⊢ '() q '(Bool  -> Nat)))))
;30
  (length (car (run 30 q (⊢ '() q '(Nat  -> (Nat  -> Nat))))))
;30
  (length (car (run 100 q
             (fresh (e t)
               (⊢ '() e t)
               (== `(,e ,t) q)))))
;100
  (length (car (run 100 q
             (fresh (g e t)
               (⊢ g e t)
               (== `(,g ,e ,t) q)))))
;100
  (length
   (car (run 100 q
     (fresh (g v)
       (⊢ g `(var ,v) 'Nat)
       (== `(,g ,v) q)))))
;100
  (run 1 q
       (fresh (g)
	 (⊢ g
	      '((fix (λ (!)
		       (λ (n)
			 (if (zero? n)
			     1
			     (* n (! (sub1 n)))))))
		5)
	      q)))
;(Nat)
  (run 1 q
       (fresh (g)
	 (⊢ g
	      '((fix (λ (!)
		       (λ (n)
			 (* n (! (sub1 n))))))
		5)
	      q)))
;(Nat)
