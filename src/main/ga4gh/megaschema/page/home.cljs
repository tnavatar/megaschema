(ns ga4gh.megaschema.page.home
  (:require [re-frame.core :as re-frame]
            [reitit.frontend.easy :as rfe]
            [clojure.string :as s]
            [ga4gh.megaschema.schema :as schema]))

(def mobile-menu
  [:div
  {:class "relative z-50 lg:hidden",
   :role "dialog",
   :aria-modal "true"}
  (comment
   "Off-canvas menu backdrop, show/hide based on off-canvas menu state.\n\n      Entering: \"transition-opacity ease-linear duration-300\"\n        From: \"opacity-0\"\n        To: \"opacity-100\"\n      Leaving: \"transition-opacity ease-linear duration-300\"\n        From: \"opacity-100\"\n        To: \"opacity-0\"")
  [:div {:class "fixed inset-0 bg-gray-900/80", :aria-hidden "true"}]
  [:div
   {:class "fixed inset-0 flex"}
   (comment
    "Off-canvas menu, show/hide based on off-canvas menu state.\n\n        Entering: \"transition ease-in-out duration-300 transform\"\n          From: \"-translate-x-full\"\n          To: \"translate-x-0\"\n        Leaving: \"transition ease-in-out duration-300 transform\"\n          From: \"translate-x-0\"\n          To: \"-translate-x-full\"")
   [:div
    {:class "relative mr-16 flex w-full max-w-xs flex-1"}
    (comment
     "Close button, show/hide based on off-canvas menu state.\n\n          Entering: \"ease-in-out duration-300\"\n            From: \"opacity-0\"\n            To: \"opacity-100\"\n          Leaving: \"ease-in-out duration-300\"\n            From: \"opacity-100\"\n            To: \"opacity-0\"")
    [:div
     {:class "absolute top-0 left-full flex w-16 justify-center pt-5"}
     [:button
      {:type "button", :class "-m-2.5 p-2.5"}
      [:span {:class "sr-only"} "Close sidebar"]
      [:svg
       {:class "size-6 text-white",
        :fill "none",
        :viewBox "0 0 24 24",
        :stroke-width "1.5",
        :stroke "currentColor",
        :aria-hidden "true",
        :data-slot "icon"}
       [:path
        {:stroke-linecap "round",
         :stroke-linejoin "round",
         :d "M6 18 18 6M6 6l12 12"}]]]]
    (comment
     "Sidebar component, swap this element with another sidebar if you like")
    [:div
     {:class
      "flex grow flex-col gap-y-5 overflow-y-auto bg-white px-6 pb-2"}
     [:div
      {:class "flex h-16 shrink-0 items-center"}
      [:img
       {:class "h-8 w-auto",
        :src
        "img/logo-full-color.svg",
        :alt "Your Company"}]]
     [:nav
      {:class "flex flex-1 flex-col"}
      [:ul
       {:role "list", :class "flex flex-1 flex-col gap-y-7"}
       [:li
        [:ul
         {:role "list", :class "-mx-2 space-y-1"}
         [:li
          (comment
           "Current: \"bg-gray-50 text-indigo-600\", Default: \"text-gray-700 hover:text-indigo-600 hover:bg-gray-50\"")
          [:a
           {:href "#",
            :class
            "group flex gap-x-3 rounded-md bg-gray-50 p-2 text-sm/6 font-semibold text-indigo-600"}
           [:svg
            {:class "size-6 shrink-0 text-indigo-600",
             :fill "none",
             :viewBox "0 0 24 24",
             :stroke-width "1.5",
             :stroke "currentColor",
             :aria-hidden "true",
             :data-slot "icon"}
            [:path
             {:stroke-linecap "round",
              :stroke-linejoin "round",
              :d
              "m2.25 12 8.954-8.955c.44-.439 1.152-.439 1.591 0L21.75 12M4.5 9.75v10.125c0 .621.504 1.125 1.125 1.125H9.75v-4.875c0-.621.504-1.125 1.125-1.125h2.25c.621 0 1.125.504 1.125 1.125V21h4.125c.621 0 1.125-.504 1.125-1.125V9.75M8.25 21h8.25"}]]
           "Dashboard"]]
         [:li
          [:a
           {:href "#",
            :class
            "group flex gap-x-3 rounded-md p-2 text-sm/6 font-semibold text-gray-700 hover:bg-gray-50 hover:text-indigo-600"}
           [:svg
            {:class
             "size-6 shrink-0 text-gray-400 group-hover:text-indigo-600",
             :fill "none",
             :viewBox "0 0 24 24",
             :stroke-width "1.5",
             :stroke "currentColor",
             :aria-hidden "true",
             :data-slot "icon"}
            [:path
             {:stroke-linecap "round",
              :stroke-linejoin "round",
              :d
              "M15 19.128a9.38 9.38 0 0 0 2.625.372 9.337 9.337 0 0 0 4.121-.952 4.125 4.125 0 0 0-7.533-2.493M15 19.128v-.003c0-1.113-.285-2.16-.786-3.07M15 19.128v.106A12.318 12.318 0 0 1 8.624 21c-2.331 0-4.512-.645-6.374-1.766l-.001-.109a6.375 6.375 0 0 1 11.964-3.07M12 6.375a3.375 3.375 0 1 1-6.75 0 3.375 3.375 0 0 1 6.75 0Zm8.25 2.25a2.625 2.625 0 1 1-5.25 0 2.625 2.625 0 0 1 5.25 0Z"}]]
           "Team"]]
         [:li
          [:a
           {:href "#",
            :class
            "group flex gap-x-3 rounded-md p-2 text-sm/6 font-semibold text-gray-700 hover:bg-gray-50 hover:text-indigo-600"}
           [:svg
            {:class
             "size-6 shrink-0 text-gray-400 group-hover:text-indigo-600",
             :fill "none",
             :viewBox "0 0 24 24",
             :stroke-width "1.5",
             :stroke "currentColor",
             :aria-hidden "true",
             :data-slot "icon"}
            [:path
             {:stroke-linecap "round",
              :stroke-linejoin "round",
              :d
              "M2.25 12.75V12A2.25 2.25 0 0 1 4.5 9.75h15A2.25 2.25 0 0 1 21.75 12v.75m-8.69-6.44-2.12-2.12a1.5 1.5 0 0 0-1.061-.44H4.5A2.25 2.25 0 0 0 2.25 6v12a2.25 2.25 0 0 0 2.25 2.25h15A2.25 2.25 0 0 0 21.75 18V9a2.25 2.25 0 0 0-2.25-2.25h-5.379a1.5 1.5 0 0 1-1.06-.44Z"}]]
           "Projects"]]
         [:li
          [:a
           {:href "#",
            :class
            "group flex gap-x-3 rounded-md p-2 text-sm/6 font-semibold text-gray-700 hover:bg-gray-50 hover:text-indigo-600"}
           [:svg
            {:class
             "size-6 shrink-0 text-gray-400 group-hover:text-indigo-600",
             :fill "none",
             :viewBox "0 0 24 24",
             :stroke-width "1.5",
             :stroke "currentColor",
             :aria-hidden "true",
             :data-slot "icon"}
            [:path
             {:stroke-linecap "round",
              :stroke-linejoin "round",
              :d
              "M6.75 3v2.25M17.25 3v2.25M3 18.75V7.5a2.25 2.25 0 0 1 2.25-2.25h13.5A2.25 2.25 0 0 1 21 7.5v11.25m-18 0A2.25 2.25 0 0 0 5.25 21h13.5A2.25 2.25 0 0 0 21 18.75m-18 0v-7.5A2.25 2.25 0 0 1 5.25 9h13.5A2.25 2.25 0 0 1 21 11.25v7.5"}]]
           "Calendar"]]
         [:li
          [:a
           {:href "#",
            :class
            "group flex gap-x-3 rounded-md p-2 text-sm/6 font-semibold text-gray-700 hover:bg-gray-50 hover:text-indigo-600"}
           [:svg
            {:class
             "size-6 shrink-0 text-gray-400 group-hover:text-indigo-600",
             :fill "none",
             :viewBox "0 0 24 24",
             :stroke-width "1.5",
             :stroke "currentColor",
             :aria-hidden "true",
             :data-slot "icon"}
            [:path
             {:stroke-linecap "round",
              :stroke-linejoin "round",
              :d
              "M15.75 17.25v3.375c0 .621-.504 1.125-1.125 1.125h-9.75a1.125 1.125 0 0 1-1.125-1.125V7.875c0-.621.504-1.125 1.125-1.125H6.75a9.06 9.06 0 0 1 1.5.124m7.5 10.376h3.375c.621 0 1.125-.504 1.125-1.125V11.25c0-4.46-3.243-8.161-7.5-8.876a9.06 9.06 0 0 0-1.5-.124H9.375c-.621 0-1.125.504-1.125 1.125v3.5m7.5 10.375H9.375a1.125 1.125 0 0 1-1.125-1.125v-9.25m12 6.625v-1.875a3.375 3.375 0 0 0-3.375-3.375h-1.5a1.125 1.125 0 0 1-1.125-1.125v-1.5a3.375 3.375 0 0 0-3.375-3.375H9.75"}]]
           "Documents"]]
         [:li
          [:a
           {:href "#",
            :class
            "group flex gap-x-3 rounded-md p-2 text-sm/6 font-semibold text-gray-700 hover:bg-gray-50 hover:text-indigo-600"}
           [:svg
            {:class
             "size-6 shrink-0 text-gray-400 group-hover:text-indigo-600",
             :fill "none",
             :viewBox "0 0 24 24",
             :stroke-width "1.5",
             :stroke "currentColor",
             :aria-hidden "true",
             :data-slot "icon"}
            [:path
             {:stroke-linecap "round",
              :stroke-linejoin "round",
              :d "M10.5 6a7.5 7.5 0 1 0 7.5 7.5h-7.5V6Z"}]
            [:path
             {:stroke-linecap "round",
              :stroke-linejoin "round",
              :d "M13.5 10.5H21A7.5 7.5 0 0 0 13.5 3v7.5Z"}]]
           "Reports"]]]]
       [:li
        [:div
         {:class "text-xs/6 font-semibold text-gray-400"}
         "Your teams"]
        [:ul
         {:role "list", :class "-mx-2 mt-2 space-y-1"}
         [:li
          (comment
           "Current: \"bg-gray-50 text-indigo-600\", Default: \"text-gray-700 hover:text-indigo-600 hover:bg-gray-50\"")
          [:a
           {:href "#",
            :class
            "group flex gap-x-3 rounded-md p-2 text-sm/6 font-semibold text-gray-700 hover:bg-gray-50 hover:text-indigo-600"}
           [:span
            {:class
             "flex size-6 shrink-0 items-center justify-center rounded-lg border border-gray-200 bg-white text-[0.625rem] font-medium text-gray-400 group-hover:border-indigo-600 group-hover:text-indigo-600"}
            "H"]
           [:span {:class "truncate"} "Heroicons"]]]
         [:li
          [:a
           {:href "#",
            :class
            "group flex gap-x-3 rounded-md p-2 text-sm/6 font-semibold text-gray-700 hover:bg-gray-50 hover:text-indigo-600"}
           [:span
            {:class
             "flex size-6 shrink-0 items-center justify-center rounded-lg border border-gray-200 bg-white text-[0.625rem] font-medium text-gray-400 group-hover:border-indigo-600 group-hover:text-indigo-600"}
            "T"]
           [:span {:class "truncate"} "Tailwind Labs"]]]
         [:li
          [:a
           {:href "#",
            :class
            "group flex gap-x-3 rounded-md p-2 text-sm/6 font-semibold text-gray-700 hover:bg-gray-50 hover:text-indigo-600"}
           [:span
            {:class
             "flex size-6 shrink-0 items-center justify-center rounded-lg border border-gray-200 bg-white text-[0.625rem] font-medium text-gray-400 group-hover:border-indigo-600 group-hover:text-indigo-600"}
            "W"]
           [:span {:class "truncate"} "Workcation"]]]]]]]]]]])

(def mobile-menu-button
     [:div
    {:class
     "sticky top-0 z-40 flex items-center gap-x-6 bg-white px-4 py-4 shadow-xs sm:px-6 lg:hidden"}
    [:button
     {:type "button", :class "-m-2.5 p-2.5 text-gray-700 lg:hidden"}
     [:span {:class "sr-only"} "Open sidebar"]
     [:svg
      {:class "size-6",
       :fill "none",
       :viewBox "0 0 24 24",
       :stroke-width "1.5",
       :stroke "currentColor",
       :aria-hidden "true",
       :data-slot "icon"}
      [:path
       {:stroke-linecap "round",
        :stroke-linejoin "round",
        :d "M3.75 6.75h16.5M3.75 12h16.5m-16.5 5.25h16.5"}]]]
    [:div
     {:class "flex-1 text-sm/6 font-semibold text-gray-900"}
     "Dashboard"]
    [:a
     {:href "#"}
     [:span {:class "sr-only"} "Your profile"]
     [:img
      {:class "size-8 rounded-full bg-gray-50",
       :src
       "https://images.unsplash.com/photo-1472099645785-5658abf4ff4e?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=facearea&facepad=2&w=256&h=256&q=80",
       :alt ""}]]])


(def selected-item
  [:li
   (comment
     "Current: \"bg-gray-50 text-indigo-600\", Default: \"text-gray-700 hover:text-indigo-600 hover:bg-gray-50\"")
   [:a
    {:href "#",
     :class
     "group flex gap-x-3 rounded-md bg-gray-50 p-2 text-sm/6 font-semibold text-indigo-600"}
    [:svg
     {:class "size-6 shrink-0 text-indigo-600",
      :fill "none",
      :viewBox "0 0 24 24",
      :stroke-width "1.5",
      :stroke "currentColor",
      :aria-hidden "true",
      :data-slot "icon"}
     [:path
      {:stroke-linecap "round",
       :stroke-linejoin "round",
       :d
       "m2.25 12 8.954-8.955c.44-.439 1.152-.439 1.591 0L21.75 12M4.5 9.75v10.125c0 .621.504 1.125 1.125 1.125H9.75v-4.875c0-.621.504-1.125 1.125-1.125h2.25c.621 0 1.125.504 1.125 1.125V21h4.125c.621 0 1.125-.504 1.125-1.125V9.75M8.25 21h8.25"}]]
    "Dashboard"]])

(def item
  [:li
   [:a
    {:href "#",
     :class
     "group flex gap-x-3 rounded-md p-2 text-sm/6 font-semibold text-gray-700 hover:bg-gray-50 hover:text-indigo-600"}
    [:svg
     {:class
      "size-6 shrink-0 text-gray-400 group-hover:text-indigo-600",
      :fill "none",
      :viewBox "0 0 24 24",
      :stroke-width "1.5",
      :stroke "currentColor",
      :aria-hidden "true",
      :data-slot "icon"}
     [:path
      {:stroke-linecap "round",
       :stroke-linejoin "round",
       :d
       "M15 19.128a9.38 9.38 0 0 0 2.625.372 9.337 9.337 0 0 0 4.121-.952 4.125 4.125 0 0 0-7.533-2.493M15 19.128v-.003c0-1.113-.285-2.16-.786-3.07M15 19.128v.106A12.318 12.318 0 0 1 8.624 21c-2.331 0-4.512-.645-6.374-1.766l-.001-.109a6.375 6.375 0 0 1 11.964-3.07M12 6.375a3.375 3.375 0 1 1-6.75 0 3.375 3.375 0 0 1 6.75 0Zm8.25 2.25a2.625 2.625 0 1 1-5.25 0 2.625 2.625 0 0 1 5.25 0Z"}]]
    "Team"]])

(def magnifying-glass-outline
  [:svg
   {:xmlns "http://www.w3.org/2000/svg",
    :fill "none",
    :viewBox "0 0 24 24",
    :stroke-width "1.5",
    :stroke "currentColor",
    :class "size-6"}
   [:path
    {:stroke-linecap "round",
     :stroke-linejoin "round",
     :d
     "m21 21-5.197-5.197m0 0A7.5 7.5 0 1 0 5.196 5.196a7.5 7.5 0 0 0 10.607 10.607Z"}]])

(re-frame/reg-event-db
 ::update-search
 (fn [db [_ t]]
   (js/console.log t)
   (assoc db ::search-text t)))

(re-frame/reg-sub
 ::search-text
 :-> ::search-text)

(defn filter-schema [text]
  (filter
   #(re-find
     (-> text s/lower-case re-pattern)
     (-> % key name s/lower-case))
   schema/schema))

(re-frame/reg-sub
 ::filtered-classes
 (fn [{::keys [search-text]} _]
   (if search-text
     (filter-schema search-text)
     schema/schema)))

(defn search []
  [:div
   {:class "mt-2 mb-8 p-2"}
   [:input
    {:type "search",
     :name "search",
     :id "search",
     :class
     "block w-full rounded-md bg-white px-3 py-1.5 text-base text-gray-900 outline-1 -outline-offset-1 outline-gray-300 placeholder:text-gray-400 focus:outline-2 focus:-outline-offset-2 focus:outline-indigo-600 sm:text-sm/6",
     :placeholder "search"
     :on-change #(re-frame/dispatch
                  [::update-search
                   (-> % .-target .-value)])}]])

(re-frame/reg-event-db
 ::select-class
 (fn [db [_ class]]
   (js/console.log (name class))
   (assoc db ::selected-class class)))

(re-frame/reg-sub
 ::selected-class
 :-> ::selected-class)

(def source-icon
  {:ga4gh/va-spec
   [:svg
    {:xmlns "http://www.w3.org/2000/svg",
     :fill "none",
     :viewBox "0 0 24 24",
     :stroke-width "1.5",
     :stroke "currentColor",
     :class "size-6"}
    [:path
     {:stroke-linecap "round",
      :stroke-linejoin "round",
      :d
      "M9.568 3H5.25A2.25 2.25 0 0 0 3 5.25v4.318c0 .597.237 1.17.659 1.591l9.581 9.581c.699.699 1.78.872 2.607.33a18.095 18.095 0 0 0 5.223-5.223c.542-.827.369-1.908-.33-2.607L11.16 3.66A2.25 2.25 0 0 0 9.568 3Z"}]
    [:path
     {:stroke-linecap "round",
      :stroke-linejoin "round", 
      :d "M6 6h.008v.008H6V6Z"}]]
   :ga4gh/vrs
   [:svg
    {:xmlns "http://www.w3.org/2000/svg",
     :fill "none",
     :viewBox "0 0 24 24",
     :stroke-width "1.5",
     :stroke "currentColor",
     :class "size-6"}
    [:path
     {:stroke-linecap "round",
      :stroke-linejoin "round",
      :d "M15 10.5a3 3 0 1 1-6 0 3 3 0 0 1 6 0Z"}]
    [:path
     {:stroke-linecap "round",
      :stroke-linejoin "round",
      :d
      "M19.5 10.5c0 7.142-7.5 11.25-7.5 11.25S4.5 17.642 4.5 10.5a7.5 7.5 0 1 1 15 0Z"}]]
   :ga4gh/gks-core
   [:svg
    {:xmlns "http://www.w3.org/2000/svg",
     :fill "none",
     :viewBox "0 0 24 24",
     :stroke-width "1.5",
     :stroke "currentColor",
     :class "size-6"}
    [:path
     {:stroke-linecap "round",
      :stroke-linejoin "round",
      :d
      "m21 7.5-2.25-1.313M21 7.5v2.25m0-2.25-2.25 1.313M3 7.5l2.25-1.313M3 7.5l2.25 1.313M3 7.5v2.25m9 3 2.25-1.313M12 12.75l-2.25-1.313M12 12.75V15m0 6.75 2.25-1.313M12 21.75V19.5m0 2.25-2.25-1.313m0-16.875L12 2.25l2.25 1.313M21 14.25v2.25l-2.25 1.313m-13.5 0L3 16.5v-2.25"}]]
   :ga4gh/cat-vrs
   [:svg
    {:xmlns "http://www.w3.org/2000/svg",
     :fill "none",
     :viewBox "0 0 24 24",
     :stroke-width "1.5",
     :stroke "currentColor",
     :class "size-6"}
    [:path
     {:stroke-linecap "round",
      :stroke-linejoin "round",
      :d
      "M16.5 12a4.5 4.5 0 1 1-9 0 4.5 4.5 0 0 1 9 0Zm0 0c0 1.657 1.007 3 2.25 3S21 13.657 21 12a9 9 0 1 0-2.636 6.364M16.5 12V8.25"}]]})

(defn menu-class [[class-name attrs]]
  ^{:key class-name}
  [:li
   [:a
    {:href "#",
     :class
     "group flex gap-x-3 rounded-md p-1 text-sm/6 font-semibold text-gray-700 hover:bg-gray-50 hover:text-indigo-600"
     :on-click #(re-frame/dispatch [::select-class class-name])}
    (source-icon (:source attrs))
    (name class-name)]])

(defn menu []
  [:div
   {:class
    "hidden lg:fixed lg:inset-y-0 lg:z-50 lg:flex lg:w-72 lg:flex-col"}
   [:div
    {:class
     "flex grow flex-col gap-y-5 overflow-y-auto border-r border-gray-200 bg-white px-6"}
    [:div
     {:class "flex h-16 shrink-0 items-center"}
     [:img
      {:class "h-12 w-auto",
       :src
       "img/logo-full-color.svg" 
       :alt "Your Company"}]]
    [:nav
     {:class "flex flex-1 flex-col"}
     (search)
     [:ul
      {:role "list", :class "-mx-2 space-y-1"}
      (for [c @(re-frame/subscribe [::filtered-classes])]
        (menu-class c))]]]])

"https://vrs.ga4gh.org/en/stable/concepts/MolecularVariation/index.html#molecular-variation"

(defn display-class []
  (let [c @(re-frame/subscribe [::selected-class])
        class-schema (get schema/schema c)]
    [:div
     [:div (name c)]
     [:div (:description class-schema)]
     [:div (:$comment class-schema)]
     [:ul]]))

(defn home []
  [:div
   (menu)
   [:main
    {:class "py-10 lg:pl-72"}
    [:div
     {:class "px-4 sm:px-6 lg:px-8"}
     (display-class)]]])
