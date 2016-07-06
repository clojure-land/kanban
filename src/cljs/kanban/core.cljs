(ns kanban.core
  (:require [reagent.core :as r]))

(enable-console-print!)

(def app-state
  (r/atom {:columns [{:title "Todos"
                      :cards [{:title "Learn about Reagent"}
                              {:title "Tell my friends about Lambda Island"}]}
                     {:title "Awesomize"
                      :editing true
                      :cards [{:title "Meditate"}
                              {:title "Work out"
                               :editing true}]}]}))

(defn Card [card]
  (if (:editing card)
    [:div.card.editing [:input {:type "text" :value (:title card)}]]
    [:div.card (:title card)]))

(defn NewCard []
  [:div.new-card
   "+ add new card"])

(defn Column [board col-path]
  (let [{:keys [title cards editing]} (get-in @board col-path)]
    [:div.column
     (if editing
       [:input {:type "text" :value title}]
       [:h2 title])
     (for [c cards]
       [Card c])
     [NewCard]]))

(defn NewColumn []
  [:div.new-column
   "+ add new column"])

(defn Board [board]
  [:div.board
   (for [idx (range (count (:columns @board)))]
     (let [col-path [:columns idx]]
       [Column board col-path]))
   [NewColumn]])

(r/render [Board app-state] (js/document.getElementById "app"))
