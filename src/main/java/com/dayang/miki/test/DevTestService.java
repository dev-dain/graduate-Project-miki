package com.dayang.miki.test;

import com.dayang.miki.Item.DTO.ItemDTO;
import com.dayang.miki.Item.DevItemService;
import com.dayang.miki.domain.Item;
import com.dayang.miki.domain.Item_img;
import com.dayang.miki.domain.Item_option;
import com.dayang.miki.domain.TestColor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
public class DevTestService {

    private final DevItemService devItemService;
    private final DevTestRepository devTestRepository;

    public Item findItem(Long id){ return devItemService.findById(id);}
    public List<Item_option> findItemOption(Item item){return devItemService.findItemOptionByItem(item);}
    public Item_img findItemImage(Item item){return devItemService.findItemImageByItem(item);}

    public TestColor testColor(Item_option itemOption){ return devTestRepository.findByItemOption(itemOption);}

    public TestItemDTO testItemDTO(Item item, Item_img itemImg, List<TestOptionDTO> testOptionDTOList){
        TestItemDTO testItemDTO = new TestItemDTO();
        testItemDTO.setItemId(item.getId());
        testItemDTO.setItemImage(itemImg.getItem_img());
        testItemDTO.setItemName(item.getName());
        testItemDTO.setTestOption(testOptionDTOList);

        return testItemDTO;
    }
    public TestOptionDTO testOptionDTO(TestColor testColor, Item_option itemOption){
        TestOptionDTO testOptionDTO = new TestOptionDTO();
        testOptionDTO.setOptionId(itemOption.getId());
        testOptionDTO.setOptionName(itemOption.getItem_option_name());
        testOptionDTO.setOptionColorId(testColor.getId());
        testOptionDTO.setOptionColor(testColor.getColorCode());
        testOptionDTO.setOptionColorAlpha(testColor.getAlpha());
        testOptionDTO.setOptionFaceLocation(testColor.getFace_location());

        return testOptionDTO;
    }
    public TestItemDTO testItem(Long itemId){
        Item item = findItem(itemId);
        List<Item_option> itemOptionList = findItemOption(item);
        Item_img itemImg = findItemImage(item);

        List<TestOptionDTO> testOptionDTOList = new ArrayList<>();


        for(Item_option itemOption : itemOptionList){
            TestColor color = testColor(itemOption);
            TestOptionDTO testOptionDTO = testOptionDTO(color,itemOption);
            testOptionDTOList.add(testOptionDTO);
        }

        return testItemDTO(item, itemImg, testOptionDTOList);
    }
}
