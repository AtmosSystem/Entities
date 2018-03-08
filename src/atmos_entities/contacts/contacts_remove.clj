(in-ns 'atmos-entities.core)


(def ^:private remove-persist-contact-base* (-> (delete* contacts)))
(def ^:private remove-persist-contact-by #(-> remove-persist-contact-base*
                                              (where %)
                                              delete))

(defremove-entity :contact [id] get-contact remove-persist-contact-by :id)
(defremove-entity :contacts [entity-id] get-entity remove-persist-contact-by :entity_id)