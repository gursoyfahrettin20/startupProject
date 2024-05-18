package com.ws.startupProject.Slider;

import com.ws.startupProject.Slider.dto.SliderCreate;
import com.ws.startupProject.configuration.CurrentUser;
import com.ws.startupProject.productToImages.dto.ProductToImagesCreate;
import com.ws.startupProject.shared.GenericMessage;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RequestMapping("api/v1")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class SliderController {

    @Autowired
    SliderService service;

    // Slider resimlerinin kaydedilmesi
    @PostMapping("/newSlider")
    GenericMessage createSlider(@Valid @RequestBody SliderCreate sliderCreates, @AuthenticationPrincipal CurrentUser currentUser) {
        GenericMessage message = new GenericMessage("Siteye Giriş yapmadınız.");
        if (currentUser.getIsAdministrator()) {
            service.save(sliderCreates.toSlider(), currentUser);
            message = new GenericMessage("Image is created");
        }
        return message;
    }

    // Slider resimlerinin listelenmesi
    @GetMapping("/slider")
    public List<Slider> getSlider(@AuthenticationPrincipal CurrentUser currentUser) {
        if (currentUser.getIsAdministrator()) {
            return service.getSliderList();
        }
        return null;
    }

    // Slider resimlerinin silinmesi
    @DeleteMapping("/slider/{id}")
    GenericMessage deleteSlider(@PathVariable String id, @AuthenticationPrincipal CurrentUser currentUser) {
        GenericMessage message = new GenericMessage("Sitenin Yöneticisi Değilsiniz.");
        if (currentUser.getIsAdministrator()) {
            service.deleteSlider(id);
            message = new GenericMessage("Slider image is Delete");
        }
        return message;
    }

    // Slider resimlerinin güncellenmesi
    @PutMapping("/slider/{id}")
    GenericMessage updateSlider(@Valid @PathVariable Long id,  @RequestBody Slider slider, @AuthenticationPrincipal CurrentUser currentUser) {
        GenericMessage message = new GenericMessage("Sitenin Yöneticisi Değilsiniz.");
        if (currentUser.getIsAdministrator() && Objects.equals(id, currentUser.getId())) {
            service.updateSlider(slider);
            message = new GenericMessage("Slider image is Update");
        }
        return message;
    }
}