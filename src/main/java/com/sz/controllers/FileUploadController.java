package com.sz.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileSystemUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

/**
 * Created by lenovo on 10.09.2017.
 */
@Controller
public class FileUploadController {
    private String rootFolder = "src/main/resources/static/";
    private String location = "upload-dir";

    private Path rootLocation;
    private String fileName;

    public FileUploadController() {
        this.rootLocation = Paths.get(rootFolder + location);
        try {
            FileSystemUtils.deleteRecursively(rootLocation.toFile());
            Files.createDirectories(rootLocation);
        }
        catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    @GetMapping("/")
    public String uploadedForm(Model model) throws IOException {

//        model.addAttribute("files", storageService.loadAll().map(
//                path -> MvcUriComponentsBuilder.fromMethodName(FileUploadController.class,
//                        "serveFile", path.getFileName().toString()).build().toString())
//                .collect(Collectors.toList()));

        return "uploadForm";
    }

//    @GetMapping("/files/{filename:.+}")
//    @ResponseBody
//    public ResponseEntity<Resource> serveFile(@PathVariable String filename) {
//
//        Resource file = storageService.loadAsResource(filename);
//        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
//                "attachment; filename=\"" + file.getFilename() + "\"").body(file);
//    }

    @PostMapping("/")
    public String handleFileUpload(@RequestParam("file") MultipartFile file,
                                   RedirectAttributes redirectAttributes) {

//        storageService.store(file);
        this.fileName = StringUtils.cleanPath(file.getOriginalFilename());
        try {
            if (file.isEmpty()) {
                System.out.println("Failed to store empty file " + this.fileName);
            }
            if (this.fileName.contains("..")) {
                System.out.println(
                        "Cannot store file with relative path outside current directory "
                                + this.fileName);
            }
            Files.copy(file.getInputStream(), this.rootLocation.resolve(this.fileName),
                    StandardCopyOption.REPLACE_EXISTING);
        }
        catch (IOException e) {
            System.out.println("Failed to store file " + this.fileName + " " + e.getMessage());
        }

        redirectAttributes.addFlashAttribute("message",
                "You successfully uploaded " + file.getOriginalFilename() + "!");

        redirectAttributes.addFlashAttribute("fileName", File.separator + location + File.separator + fileName);
//        return "redirect:/";
        return "redirect:/";

//        redirectAttributes.addAttribute("filename",file.getOriginalFilename());
//        return "showForm";
    }

//    @ExceptionHandler(StorageFileNotFoundException.class)
//    public ResponseEntity<?> handleStorageFileNotFound(StorageFileNotFoundException exc) {
//        return ResponseEntity.notFound().build();
//    }

}
