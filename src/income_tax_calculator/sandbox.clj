(ns income-tax-calculator.sandbox
  (:require [clojure.pprint :as pp]
            [clojure.data.json :as json]))

(def discountData {:text  {:prefix "Up to"
                           :body "$99"
                           :suffix "disc"}
                   :color "#000"})

;; Clojure:
(pp/pprint
 (assoc-in discountData [:text :body] "$10"))

(json/read-str "{\"a\":1,\"b\":2}" :key-fn str) ;; => {"a" 1, "b" 2}
(json/read-str "{\"a\":1,\"b\":2}" :key-fn #(keyword "com.example" %)) ;; => #:com.example{:a 1, :b 2}
(json/write-str {:a 1 :b 2}) ;; => "{\"a\":1,\"b\":2}"

;; Shitty JS
;; > JSON.stringify({"keyX": undefined, "keyY": "value"})
;; '{"keyY":"value"}'

(json/write-str {"kryX" nil "keyY" "value"})
;; => "{\"kryX\":null,\"keyY\":\"value\"}"

(json/read-str "{\"a\":1,\"b\":2}")
;;=> {"a" 1, "b" 2}

;; JavaScript:
;; export const discountData = {
;;   text: {  prefix: 'Up to', body: '50%', suffix: 'off' },
;;   color: '#000',
;; }
;; {...discountData, text: {...discountData.text, body: "$10"}}
