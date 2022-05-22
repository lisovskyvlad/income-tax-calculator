(ns income-tax-calculator.core
  (:require [income-tax-calculator.spain :as sp]
            [income-tax-calculator.germany :as ger]
            [income-tax-calculator.utils :refer [round2]]))

(defn report-country [func income]
  (let [tax (round2 2 (func income))
        after-tax (round2 0 (- income tax))
        percent (round2 2 (* 100 (/ tax income)))
        per-month (round2 2 (/ after-tax 12))]
    [tax after-tax percent per-month]))

(map
 #(vector
   %
   {:germany (report-country ger/income-tax %)
    :spain (report-country sp/income-tax %)})
 (range 40000 130000 5000))
