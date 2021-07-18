package com.dayang.miki.controller;

import com.dayang.miki.domain.Item_option;
import com.dayang.miki.domain.Position;
import com.dayang.miki.domain.Store;
import com.dayang.miki.domain.StoreQuantity;
import com.dayang.miki.service.ItemService;
import com.dayang.miki.service.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import java.util.*;

@Controller
@RequiredArgsConstructor
public class StoreController {
    private final StoreService storeService;
    private final ItemService itemService;

    @GetMapping("/stores")
    public String stores(Model model){
        List<Store> stores = storeService.findAllStore();
        List<Position> positions = storeService.findAllPosition();
        model.addAttribute("stores", stores);
        model.addAttribute("positions", positions);
        return "store/stores";
    }

    @GetMapping("/soldout/{store_id}/{item_option_id}")
    public String soldout(@PathVariable("store_id")Long id, @PathVariable("item_option_id")Long option_id, Model model){
            Store store = storeService.findById(id);
            Position position = storeService.findSinglePosition(store);
            List<Store> stores = storeService.findAllStore();
            List<Position> positions = storeService.findAllPosition();
            Item_option item_option = itemService.findItemOptionById(option_id);
            List<StoreQuantity>storeQuantities = storeService.storeQuantities(item_option);
            List<NearStore> nearStores = new ArrayList<>();
            for(int i=0; i<positions.size(); i++){
                double tmp = storeService.positionDist(position.getLatitude(), positions.get(i).getLatitude(),
                        position.getLongitude(), positions.get(i).getLongitude());
                NearStore nearStore = new NearStore();
                nearStore.setStore(stores.get(i));
                nearStore.setKm(Math.round(tmp*100)/100.0);
                if(storeQuantities.get(i).getStock_quantity()==0) continue;
                nearStore.setStock(storeQuantities.get(i).getStock_quantity());
                nearStores.add(nearStore);
            }
            Collections.sort(nearStores, new Comparator<NearStore>() {
                @Override
                public int compare(NearStore n1, NearStore n2) {
                    return (int) (n1.getKm() - n2.getKm());
                }
            });
            List<NearStore> ans = new ArrayList<>();
            ans.add(nearStores.get(0));
            ans.add(nearStores.get(1));
            ans.add(nearStores.get(2));

            model.addAttribute("nearStores",ans); //같은 인덱스 순서대로 들어있어용

        return "store/near-store";

    }

    @GetMapping("/storeDistance/{store_id}")
    public String distance(@PathVariable("store_id")Long id, Model model){
        Store store = storeService.findById(id);
        Position position = storeService.findSinglePosition(store);
        List<Store> stores = storeService.findAllStore();
        List<Position> positions = storeService.findAllPosition();
        List<Double> dist = new ArrayList<>();
        for(Position position1 : positions){
            dist.add(storeService.positionDist(position.getLatitude(), position1.getLatitude(),
                    position.getLongitude(), position1.getLongitude()));
        }
        model.addAttribute("store", stores);
        model.addAttribute("distance",dist); //같은 인덱스 순서대로 들어있어용
        model.addAttribute("positions", positions);

        return "store/store-content";
    }


    // 임시로 만든 login 
    @GetMapping("/login")
    public String store_login(){
        return "login/login";
    }

    @GetMapping("/admin/login")
    public String login(@PathVariable("password") String passwrd, @PathVariable("store_name") String name){

        return "redirect:/";
    }
}