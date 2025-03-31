(ns ga4gh.megaschema
  (:require ["react-dom/client" :refer [createRoot]]
            [reagent.core :as reagent]
            [goog.dom :as gdom]
            [reagent.dom :as rdom]
            [reagent.core :as r]
            [reitit.frontend :as rf]
            [reitit.frontend.easy :as rfe]
            [re-frame.core :as re-frame]
            [ga4gh.megaschema.routes :as routes]))

(enable-console-print!)

(defonce root (createRoot (gdom/getElement "app")))

(re-frame/reg-event-db ::initialize-db
  (fn [db _]
    (if db
      db
      {:current-route nil})))

(defn ^:dev/after-load render-root []
  (println "[main] reloaded lib:")
  (routes/init-routes!)  
  (.render root (reagent/as-element [routes/router-component
                               {:router routes/router}])))

(defn ^:export init []
  (re-frame/clear-subscription-cache!)
  (re-frame/dispatch-sync [::initialize-db])
  (render-root))
