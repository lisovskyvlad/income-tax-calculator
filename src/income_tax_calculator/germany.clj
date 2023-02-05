(ns income-tax-calculator.germany
  (:require [income-tax-calculator.utils :refer [round2]]))

;; https://www.gesetze-im-internet.de/estg/__32a.html

(def grundfreibetrag 10908)

;; 1. Grundfreibetrag --> 0
(defn bracket1 []
  0)

;; 2. von 10 909 Euro bis 15 999 Euro:
;; (979,18 · y + 1 400) · y;
(defn bracket2 [zvE]
  (let [y (/ (- zvE grundfreibetrag) 10000.0)
        yy (+ (* 979.18 y) 1400)
        Est (* yy y)]
    Est))

(comment
  (round2 1 (bracket2 11000)) ;; => 13.0
  (round2 1 (bracket2 14000)) ;; => 526.5
  )

;; 3. von 16 000 Euro bis 62 809 Euro:
;; (192,59 · z + 2 397) · z + 966,53;
(defn bracket3 [zvE]
  (let [z (/ (- zvE 15999) 10000.0)
        zz (+ (* 192.59 z) 2397)
        Est (+ (* zz z) 966.53)]
    Est))

(comment
  (round2 2 (bracket3 30000)) ;; => 4700.1
  (round2 2 (bracket3 55000)) ;; => 13244.51
  (round2 2 (bracket3 62809)) ;; => 16406.87
  )

;; 4. von 62 810 Euro bis 277 825 Euro:
;; 0,42 · x – 9 972,98;
(defn bracket4 [zvE]
  (- (* 0.42 zvE) 9972.98))

(comment
  (round2 2 (bracket3 62810)) ;; => 16407.29
  (round2 2 (bracket3 95000)) ;; => 31922.92
  )

;; 5. von 277 826 Euro an:
;; 0,45 · x – 18 307,73.
(defn bracket5 [zvE]
  (- (* 0.45 zvE) 18307.73))

(comment
  (bracket5 300000) ;; => 116692.27
  (bracket5 500000) ;; => 206692.27
  )

(defn income-tax [income]
  (cond
    (<= income grundfreibetrag) (bracket1)
    (< grundfreibetrag income 15999) (bracket2 income)
    (<= 16000 income 62809) (bracket3 income)
    (<= 62810 income 277825) (bracket4 income)
    :else
    (bracket5 income)))
