package venkat.org.springframework.springrecipe.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import venkat.org.springframework.springrecipe.command.RecipeCommand;
import venkat.org.springframework.springrecipe.services.ImageService;
import venkat.org.springframework.springrecipe.services.RecipeService;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

@Controller
@RequiredArgsConstructor
@Slf4j
public class ImageController {

    private static final String VIEW_NAME_RECIPE_IMAGE_FORM = "/recipe/imageUploadForm";

    private final RecipeService recipeService;
    private final ImageService imageService;

    @GetMapping(path = "/recipe/{recipeId}/image")
    public String handleImagePost(@PathVariable final Long recipeId, final Model model) {
        model.addAttribute("recipe", recipeService.findRecipeById(recipeId));
        return VIEW_NAME_RECIPE_IMAGE_FORM;
    }

    @PostMapping(path = "/recipe/{recipeId}/image")
    public String handleImagePost(@PathVariable final Long recipeId, @RequestParam("file") MultipartFile file) {
        imageService.saveImageFile(recipeId, file);
        return "redirect:/recipe/" + recipeId + "/view";
    }

    @GetMapping(path = "/recipe/{recipeId}/recipeImage")
    public void renderRecipeImage(@PathVariable final Long recipeId, HttpServletResponse servletResponse) throws IOException {
        log.info("renderRecipeImage, Input Recipe ID::" + recipeId);
        RecipeCommand recipeCommand = recipeService.findRecipeById(recipeId);
        if (recipeCommand.getImage() != null && recipeCommand.getImage().length > 0) {
            byte[] byteArray = new byte[recipeCommand.getImage().length];
            int i = 0;
            for (Byte wrapperByte : recipeCommand.getImage()) {
                byteArray[i++] = wrapperByte;
            }
            servletResponse.setContentType("image/jpeg");
            InputStream is = new ByteArrayInputStream(byteArray);
            IOUtils.copy(is, servletResponse.getOutputStream());
        }
    }
}
