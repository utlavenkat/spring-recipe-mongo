package venkat.org.springframework.springrecipe.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import venkat.org.springframework.springrecipe.domain.Recipe;
import venkat.org.springframework.springrecipe.repositories.RecipeRepository;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {

    private final RecipeRepository recipeRepository;

    @Override
    public void saveImageFile(Long recipeId, MultipartFile file) {
        Recipe recipe = recipeRepository.findById(recipeId).orElse(null);
        if (recipe != null) {
            try {
                byte[] primitiveBytes = file.getBytes();
                Byte[] wrapperBytes = new Byte[primitiveBytes.length];
                int i = 0;
                for (byte b : primitiveBytes) {
                    wrapperBytes[i++] = b;
                }
                recipe.setImage(wrapperBytes);
                recipeRepository.save(recipe);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
