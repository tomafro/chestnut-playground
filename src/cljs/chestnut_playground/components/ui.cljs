(ns chestnut-playground.components.ui
  (:require [com.stuartsierra.component :as component]
            [chestnut-playground.core :refer [render]]))

(defrecord UIComponent []
  component/Lifecycle
  (start [component]
    (render)
    component)
  (stop [component]
    component))

(defn new-ui-component []
  (map->UIComponent {}))
