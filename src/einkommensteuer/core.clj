(ns einkommensteuer.core)


;; b) 9985 € bis 14926 €:
;; y = (zvE - 9984) / 10000 // ESt = (1008.7 * y + 1400) * y;
(defn first [zvE]
  (let [y (/ (- zvE 9984) 10000.0)
        yy (+ (* 1008.7 y) 1400)
        Est (* yy y)]
    Est))

(comment
  (first 14000) ;; => 724.925718272
  )

;; c) 14927 € bis 58596 €:
;; z = (zvE - 14926) / 10000 // ESt = (206.43 * z + 2397) * z + 938.24;
(defn second [zvE]
  (let [z (/ (- zvE 14926) 10000.0)
        zz (+ (* 206.43 z) 2397)
        Est (+ (* zz z) 938.24)]
    Est))

(comment
  (second 50000) ;; => 11884.9496781068
  (second 58000) ;; => 15093.117009306801
  )

;; d) 58.597 € bis 277.825 €:
;; ESt = 0.42 * zvE - 9.267.53;
(defn third [zvE]
  (- (* 0.42 zvE) 9267.52))

(comment
  (third 100000) ;; => 32732.48
  (third 270000) ;; => 104132.48
  )

;; (defn income-tax [einkommen])
