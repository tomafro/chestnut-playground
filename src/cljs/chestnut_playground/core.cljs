(ns chestnut-playground.core
  (:require [hx.react :as hx :refer [defnc]]
            [hx.hooks :as hooks]
            ["react-dom" :as react-dom]))

(enable-console-print!)

; (defn widget []
;   (html [:div "Hello world!"
;         [:ul (for [n (range 1 10)]
;                 [:li {:key n} n])]
;         (html/submit-button "React!")]))





;; `defnc` creates a function that takes a props object and returns React
;; elements. You may use it just like any normal React component.
(defnc MyComponent [{:keys [initial-name]}]
  ;; use React Hooks for state management
  (let [[name update-name] (hooks/useState initial-name)]
    [:<>
     [:div "Hello "
      [:span {:style {:font-weight "bold"}} name] "!"]
     [:div [:input {:on-change #(update-name (-> % .-target .-value))}]]]))

(defnc Counter
  []
  (let [[count setCount] (hooks/useState 0)]
    [:<>
     [:div
      [:p "You clicked " count " times"]
      [:button {:onClick #(setCount (inc count))}
       "Click Me"]]]))

(defn counter-reducer
  [state action]
  (case (:type action)
    :inc (update state :count inc)
    :dec (update state :count dec)))

(defnc ClickCounter
  [initial-state]
  (let [[state dispatch] (hooks/useReducer counter-reducer
                                              (js->clj initial-state :keywordize-keys true))]
    [:<>
     [:div "Count: " (:count state)
      [:button {:onClick #(dispatch {:type :inc})} "+"]
      [:button {:onClick #(dispatch {:type :dec})} "-"]]]))

(defn TimerEffect [active time setTime]
  (hooks/useEffect
    (let [intervalId (hooks/useIRef nil)]
      (fn []
        (if (and active (not @intervalId))
          (let [previous time
               start (. js/Date now)]
            (swap! intervalId #(. js/window setInterval (fn [] (setTime (+ previous (- (. js/Date now) start))) 100))))
          (swap! intervalId #(. js/window clearInterval @intervalId)))))))

(defnc Timer []
  (let [[time setTime] (hooks/useState 0)
        [active setActive] (hooks/useState false)]
    
    (TimerEffect active time setTime)
    
    [:<>
      [:div "Active " (str active) " " (str time)]
      [:button {:onClick #(setActive true)} "+"]
      [:button {:onClick #(setActive false)} "-"]]))


(defn render []
  (react-dom/render
  ;; hx/f transforms Hiccup into a React element.
  ;; We only have to use it when we want to use hiccup outside of `defnc` / `defcomponent`
   (hx/f [Timer])
   (. js/document getElementById "app")))
