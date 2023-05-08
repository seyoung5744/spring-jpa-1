package jpabook.jpashop.repository;

import java.util.List;
import javax.persistence.EntityManager;
import jpabook.jpashop.domain.item.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ItemRepository {

    private final EntityManager em;

    public void save(Item item) {
        if (item.getId() == null) { // id가 없을 경우 새로운 객체이기에 persist해준다
            em.persist(item);
        } else { //id가 있을 경우 이미 db에서 관리되고 있는 객체이기에 merge로 업데이트 해 준다.
            em.merge(item);
        }
    }

    public Item findOne(Long id) {
        return em.find(Item.class, id);
    }

    public List<Item> findAll() {
        return em.createQuery("select i from Item i", Item.class)
            .getResultList();
    }
}
