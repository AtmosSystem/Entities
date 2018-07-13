(in-ns 'atmos-entities.core)


(defn- update-persist-entity*
  [entity]
  (update entities
          (set-fields entity)
          (where {:id (:id entity)})))

(defn- update-entity*
  [entity]
  (if-let [exists (get-entity (:id entity))]
    (do
      (update-persist-entity* entity)
      true)
    false))