(ns ga4gh.megaschema.nav
  (:require [re-frame.core :as re-frame]
            [reitit.frontend.easy :as rfe]))

(def link-arrow
  [:svg
   {:class "size-5 flex-none text-gray-400",
    :viewBox "0 0 20 20",
    :fill "currentColor",
    :aria-hidden "true",
    :data-slot "icon"}
   [:path
    {:fill-rule "evenodd",
     :d
     "M8.22 5.22a.75.75 0 0 1 1.06 0l4.25 4.25a.75.75 0 0 1 0 1.06l-4.25 4.25a.75.75 0 0 1-1.06-1.06L11.94 10 8.22 6.28a.75.75 0 0 1 0-1.06Z",
     :clip-rule "evenodd"}]])

(defn select-query []
  (for [query queries/queries]
    ^{:key query}
    [:div
     {:class "pt-1 pb-8"}
     [:ul
      {:role "list", :class "divide-y divide-gray-100"}
      [:li
       {:class "relative flex justify-between py-5"
        :on-click #(re-frame/dispatch [::execute-query query])}
       [:div
        {:class "flex gap-x-4 pr-6 sm:w-1/2 sm:flex-none"}
        [:div
         {:class "min-w-0 flex-auto"}
         [:p
          {:class "text-sm/6 font-semibold text-gray-900"}
          [:a
           {:href "#"}
           [:span {:class "absolute inset-x-0 -top-px bottom-0"}]
           (:label query)]]]]
       [:div
        {:class
         "flex items-center justify-between gap-x-4 sm:w-1/2 sm:flex-none"}
        [:p
         {:class "text-sm/5"}
         (:description query)]
        link-arrow]]]]))

(def clingen-logo
  [:div
   {:class "flex flex-1 items-center"}
   [:img
    {:class "h-8 w-auto",
     :src "/img/clingen-logo.svg"
     :alt "ClinGen"}]])

(defn nav []
  (if-not (= :hidden @(re-frame/subscribe [::nav-state]))
    [:header
     {:class "bg-white p-6 px-8"}
     [:nav
      {:class
       "mx-auto flex max-w-7xl items-center justify-between ",
       :aria-label "Global"}
      clingen-logo
      (query-label)
      (user-nav)]
     [:div
      (nav-menu)]]
    [:div]))


