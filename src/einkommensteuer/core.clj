(ns einkommensteuer.core)

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
  (bracket1 14000) ;; => 724.925718272
  )

;; c) 14927 € bis 58596 €:
;; z = (zvE - 14926) / 10000 // ESt = (206.43 * z + 2397) * z + 938.24;
(defn bracket2 [zvE]
  (let [z (/ (- zvE 14926) 10000.0)
        zz (+ (* 206.43 z) 2397)
        Est (+ (* zz z) 938.24)]
    Est))

(comment
  (bracket2 50000) ;; => 11884.9496781068
  (bracket2 58000) ;; => 15093.117009306801
  )

;; d) 58597 € bis 277825 €: ESt = 0.42 * zvE - 9.267.53;
(defn bracket3 [zvE]
  (- (* 0.42 zvE) 9267.52))

(comment
  (bracket3 100000) ;; => 32732.48
  (bracket3 270000) ;; => 104132.48
  )

;; e) ab 277.826 €: ESt = 0.45 * zvE - 17602.28
(defn bracket4 [zvE]
  (- (* 0.45 zvE) 17602.28))

(comment
  (bracket4 300000) ;; => 117397.72
  (bracket4 1000000) ;; => 432397.72
  )

(defn income-tax [einkommen]
  (cond
    (< einkommen 9984) (bracket0)
    (< 9985 einkommen 14926) (bracket1 einkommen)
    (< 14927 einkommen 58596) (bracket2 einkommen)
    (< 58597 einkommen 277825 ) (bracket3 einkommen)
    :else (bracket4 einkommen)))

(comment
  (income-tax 36000) ;; => 6906.4612485068
  (income-tax 70000) ;; => 20132.48
  (income-tax 950000);; => 409897.72
  )
