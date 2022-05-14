(ns income-tax-calculator.utils)

; http://clojure-doc.org/articles/language/functions.html
(defn round2
  "Round a double to the given precision (number of significant digits)"
  [precision d]
  (let [factor (Math/pow 10 precision)]
    (/ (Math/round (* d factor)) factor)))

(comment
  (round2 2 199.385359)
  (Math/pow 10 3)
  )
