(ns income-tax-calculator.sandbox
  (:require [clojure.pprint :as pp]))


(def discountData {:text  {:prefix "Up to"
                           :body "$99"
                           :suffix "disc"}
                   :color "#000"})

;; Clojure:
(pp/pprint
 (assoc-in discountData [:text :body] "$10"))

;; JavaScript:
;; export const discountData = {
;;   text: {  prefix: 'Up to', body: '50%', suffix: 'off' },
;;   color: '#000',
;; }
;; {...discountData, text: {...discountData.text, body: "$10"}}

(sort ["size" "title"])
