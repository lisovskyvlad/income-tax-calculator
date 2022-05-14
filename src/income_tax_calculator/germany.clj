(ns income-tax-calculator.germany
  (:require [income-tax-calculator.utils :refer [round2]]))

;; a) bis 9984 € (Grundfreibetrag): 0;
(defn bracket0 []
  0)

;; b) 9985 € bis 14926 €:
;; y = (zvE - 9984) / 10000 // ESt = (1008.7 * y + 1400) * y;
(defn bracket1 [zvE]
  (let [y (/ (- zvE 9984) 10000.0)
        yy (+ (* 1008.7 y) 1400)
        Est (* yy y)]
    Est))

(comment
  (round2 1 (bracket1 14000))                               ;; => 724.9
  )

;; c) 14927 € bis 58596 €:
;; z = (zvE - 14926) / 10000 // ESt = (206.43 * z + 2397) * z + 938.24;
(defn bracket2 [zvE]
  (let [z (/ (- zvE 14926) 10000.0)
        zz (+ (* 206.43 z) 2397)
        Est (+ (* zz z) 938.24)]
    Est))

(comment
  (round2 2 (bracket2 30000))                               ;; => 5020.54
  (round2 2 (bracket2 55000))                               ;; => 13859.09
  )

;; d) 58597 € bis 277825 €: ESt = 0.42 * zvE - 9.267.53;
(defn bracket3 [zvE]
  (- (* 0.42 zvE) 9267.52))

(comment
  (bracket3 67000)                                          ;; => 18872.48
  (bracket3 95000)                                          ;; => 30632.48
  )

;; e) ab 277.826 €: ESt = 0.45 * zvE - 17602.28
(defn bracket4 [zvE]
  (- (* 0.45 zvE) 17602.28))

(comment
  (bracket4 300000)                                         ;; => 117397.72
  (bracket4 1000000)                                        ;; => 432397.72
  (bracket4 1050000)                                        ;; => 454897.72
  )

(defn income-tax [income]
  (cond
    (< income 9984) (bracket0)
    (< 9985 income 14926) (bracket1 income)
    (< 14927 income 58596) (bracket2 income)
    (< 58597 income 277825) (bracket3 income)
    :else
    (bracket4 income)))

(comment
  (round2 2 (income-tax 75000))                             ;; => 22232.48
  (round2 2 (income-tax 30000))                             ;; => 5020.54
  )
