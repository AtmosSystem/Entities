(in-ns 'atmos-entities.core)


(defupdate-entity :contacts [contact] get-contact #(update contacts
                                                           (set-fields %)
                                                           (where {:id (:id %)})) :id)