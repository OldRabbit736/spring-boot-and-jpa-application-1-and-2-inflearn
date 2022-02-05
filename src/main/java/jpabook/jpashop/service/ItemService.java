package jpabook.jpashop.service;

import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    @Transactional  // readOnly = false (기본 설정)
    public void saveItem(Item item) {
        itemRepository.save(item);
    }

    public List<Item> findItems() {
        return itemRepository.findAll();
    }

    // 이 방법(변경감지)이 merge 보다 훨씬 안전하다!! 이 방법을 쓰자!!
    // 또한 필드마다 set으로 값을 변경하기 보다는, usecase 마다의 method를 만들어서 업데이트 하는 것이 낫다! (addStock 같은)
    // set은 왠만해서는 쓰지 말자. // set을 없애고 변경 포인트 method를 소수로 만들어 놓으면 나중에 변경 call 한 곳을 역으로 추적하기 쉬워진다!!
    @Transactional
    public void updateItem(Long itemId, String name, int price, int stockQuantity) {
        Item findItem = itemRepository.findOne(itemId);
        findItem.setName(name);
        findItem.setPrice(price);
        findItem.setStockQuantity(stockQuantity);
    }

    public Item findOne(Long itemId) {
        return itemRepository.findOne(itemId);
    }

}
