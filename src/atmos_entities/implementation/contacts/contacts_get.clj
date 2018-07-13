(in-ns 'atmos-entities.core)

(def ^:private get-persist-contacts-base* (-> (select* contacts)))
(def ^:private get-persist-contacts-by #(-> get-persist-contacts-base*
                                            (where %)
                                            select))

(defget-identity-entity :contact [id] get-persist-contacts-by :id)
(defget-entity :contacts [entity-id] get-persist-contacts-by :entity_id)