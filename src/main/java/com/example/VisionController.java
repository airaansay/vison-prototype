package com.example;

import java.util.List;

import com.google.cloud.vision.v1.AnnotateImageResponse;
import com.google.cloud.vision.v1.EntityAnnotation;
import com.google.cloud.vision.v1.Feature.Type;
import com.google.cloud.vision.v1.ImageAnnotatorClient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gcp.vision.CloudVisionTemplate;
import org.springframework.core.io.ResourceLoader;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;


@RestController
public class VisionController {

	@Autowired
	private ResourceLoader resourceLoader;

	@Autowired
	private CloudVisionTemplate cloudVisionTemplate;

	@GetMapping("/extractLabels")
	public ModelAndView extractLabels(String imageUrl, ModelMap map) {
		AnnotateImageResponse response = this.cloudVisionTemplate
				.analyzeImage(this.resourceLoader.getResource(imageUrl), Type.LABEL_DETECTION);// passing resourceLoader
																								
		/*
		 * running with no page mapping-- Resource imageResource =
		 * this.resourceLoader.getResource("file:src/main/resources/cat.jpg");
		 * AnnotateImageResponse response = this.cloudVisionTemplate.analyzeImage(
		 * imageResource, Feature.Type.LABEL_DETECTION); return
		 * response.getLabelAnnotationsList().toString();
		 */
		// This gets the annotations of the image from the response object.

		List<EntityAnnotation> annotations = response.getLabelAnnotationsList();

		map.addAttribute("annotations", annotations);
		map.addAttribute("imageUrl", imageUrl);

		return new ModelAndView("result", map);
	}
	
	@GetMapping("/extractText")
    public ModelAndView extractText(String imageUrl, ModelMap map) {
        AnnotateImageResponse text = this.cloudVisionTemplate.analyzeImage(
                this.resourceLoader.getResource(imageUrl), Type.DOCUMENT_TEXT_DETECTION);

        map.addAttribute("text", text);
        map.addAttribute("imageUrl", imageUrl);

        return new ModelAndView("result", map);
    }

	
}
