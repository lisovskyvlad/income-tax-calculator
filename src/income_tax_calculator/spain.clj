(ns income-tax-calculator.spain
  (:require [income-tax-calculator.utils :refer [round2]]))

;; https://www.idealista.com/news/fiscalidad/2022/02/04/794672-los-tramos-de-irpf-que-van-a-aplicar-las-ccaa-en-la-declaracion-de-la-renta-2021#simple-table-of-contents-5
;; Tramos de IRPF Canarias
(def canaryIslandTax
  [{:from 0 :to 12450 :taxRatio 0.09}
   {:from 12450 :to 17707 :taxRatio 0.115}
   {:from 17707 :to 33007 :taxRatio 0.14}
   {:from 33007 :to 53407 :taxRatio 0.185}
   {:from 53407 :to 90000 :taxRatio 0.235}
   {:from 90000 :to 120000 :taxRatio 0.25}
   {:from 120000 :to 300000 :taxRatio 0.26}])

;;https://www.businessinsider.es/tablas-irpf-comunidades-autonomas-cuanto-pagas-renta-625415
(def spainStateTaxBrackets
  [{:from 0 :to 12450 :taxRatio 0.095}
   {:from 12450 :to 20200 :taxRatio 0.12}
   {:from 20200 :to 35200 :taxRatio 0.15}
   {:from 35200 :to 60000 :taxRatio 0.185}
   {:from 60000 :to 300000 :taxRatio 0.225}])

(defn isIncomeInsideBracket [{:keys [from to]} income]
  (< from income to))

(defn adjustLastTaxBracket [bracket income]
  (if (isIncomeInsideBracket bracket income)
    (assoc bracket :to income)
    bracket))

(defn isTaxBracketShouldBeComputed [bracket income] (< (:from bracket) income))

(defn computeTaxSumBracket [{:keys [from to taxRatio]}]
  (* taxRatio (- to from)))

(defn income-tax-local [income]
  (->> canaryIslandTax
       (filter #(isTaxBracketShouldBeComputed % income))
       (map #(adjustLastTaxBracket % income))
       (map computeTaxSumBracket)
       (reduce +)))

(defn income-tax-state [income]
  (->> spainStateTaxBrackets
       (filter #(isTaxBracketShouldBeComputed % income))
       (map #(adjustLastTaxBracket % income))
       (map computeTaxSumBracket)
       (reduce +)))

(defn income-tax [income]
  (+ (income-tax-local income) (income-tax-state income)))

(comment
  (round2 2 (income-tax 50000))
  (round2 2 (income-tax 30000))
  )
