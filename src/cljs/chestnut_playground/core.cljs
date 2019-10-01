(ns chestnut-playground.core
  (:require
   cljsjs.react
   [sablono.core :as html :refer-macros [html]]))

(enable-console-print!)

(defn widget []
  (html [:div "Hello world!"
        [:ul (for [n (range 1 10)]
                [:li {:key n} n])]
        (html/submit-button "React!")]))

(defn render []
  (js/ReactDOM.render 
    (widget)
    (js/document.getElementById "app")))
