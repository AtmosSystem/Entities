(in-ns 'atmos-entities.implementation.core)

(def ^:private get-persist-contacts-base* (-> (select* contacts)))
(def ^:private get-persist-contacts-by #(-> get-persist-contacts-base*
                                            (where %)
                                            select))

(def ^:private get-persist-contacts-by-id #(get-persist-contacts-by {:id (:id %)}))
(def ^:private get-persist-contacts-by-entity-id #(get-persist-contacts-by {:entity_id (:entity_id %)}))

(declare get-first-contacts* get-contacts*)

(defget-identity-entity contacts get-persist-contacts-by-id)
(defget-entity contacts get-persist-contacts-by-entity-id)