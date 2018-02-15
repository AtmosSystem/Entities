(in-ns 'atmos-entities.core)

(defn- remove-persist-entities*
  [where-filter]
  (delete entities
          (where where-filter)))

(defn- remove-entities*
  [id]
  (let [where-filter {:id id}]
    (if-let [entity (get-entity id)]
      (do
        (remove-persist-entities* where-filter)
        true)
      false)))