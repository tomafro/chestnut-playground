(ns chestnut-playground.core
  (:require [hx.react :as hx :refer [defnc]]
            [hx.hooks :as hooks]
            ["react-dom" :as react-dom]))

(enable-console-print!)

(defn widget []
  (html [:div "Hello world!"
        [:ul (for [n (range 1 10)]
                [:li {:key n} n])]
        (html/submit-button "React!")]))

;; `defnc` creates a function that takes a props object and returns React
;; elements. You may use it just like any normal React component.
(defnc MyComponent [{:keys [initial-name]}]
  ;; use React Hooks for state management
  (let [[name update-name] (hooks/useState initial-name)]
    [:<>
     [:div "Hello "
      [:span {:style {:font-weight "bold"}} name] "!"]
     [:div [:input {:on-change #(update-name (-> % .-target .-value))}]]]))

(defn render []
  (react-dom/render
  ;; hx/f transforms Hiccup into a React element.
  ;; We only have to use it when we want to use hiccup outside of `defnc` / `defcomponent`
   (hx/f [MyComponent {:initial-name "React in CLJS"}])
   (. js/document getElementById "app")))
