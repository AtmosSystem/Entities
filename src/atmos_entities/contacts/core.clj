(in-ns 'atmos-entities.core)


(defentity contacts
           (pk :id)

           (entity-fields :id :entity_id :type :description :value :emergency)

           (belongs-to entities {:fk :entity_id}))

;------------------------------
; BEGIN Contacts functions
;------------------------------
(load "contacts/contacts_get")
(load "contacts/contacts_add")
(load "contacts/contacts_update")
(load "contacts/contacts_remove")
;------------------------------
; END Contacts functions
;------------------------------

(def contacts "contacts")

(extend-type Map
  IContactBasicRepository
  (add-contact [contact] (add-contacts* contact))
  (update-contact [contact] (update-contacts* contact)))

(extend-type Number
  IContactSeqRepository
  (get-contacts [entity-id] (get-contacts* entity-id))
  (remove-contacts [entity-id] (remove-contacts* entity-id))
  IContactIdentityRepository
  (get-contact [id] (get-contact* id))
  (remove-contact [id] (remove-contact* id)))