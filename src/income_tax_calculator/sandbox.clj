(ns income-tax-calculator.sandbox)

(def discountData {:text  {:prefix "Up to"
                            {:body} "$99"
                            :suffix "disc"}
                    :color "#000"})

;; Clojure:
(assoc-in discountData [:text :body] "$10")

;; JavaScript:
;; export const discountData = {
;;   text: {  prefix: 'Up to', body: '50%', suffix: 'off' },
;;   color: '#000',
;; }
;; {...discountData, text: {...discountData.text, body: "$10"}}
