package com.abiyyu.projects.libary.utils;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Component
public class ImageUpload {
    private final String UPLOAD_FOLDER_POSTERS = "C:\\Users\\ABIYYU ZAKY\\IdeaProjects\\Project_JavaWeb_Bioskop\\Bioskop_Admin\\src\\main\\resources\\static\\posters";
    public boolean uploadFilePosters(MultipartFile file) {
        boolean isUpload = false;
        try {
            Files.copy(file.getInputStream(), Paths.get(UPLOAD_FOLDER_POSTERS + File.separator + file.getOriginalFilename()) , StandardCopyOption.REPLACE_EXISTING);
            isUpload = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isUpload;
    }

    public boolean checkExistPosters(MultipartFile multipartFile){
        boolean isExist = false;
        try {
            File file = new File(UPLOAD_FOLDER_POSTERS +"\\" + multipartFile.getOriginalFilename());
            isExist = file.exists();
        }catch (Exception e){
            e.printStackTrace();
        }
        return isExist;
    }

    private final String UPLOAD_FOLDER_HEROES = "C:\\Users\\ABIYYU ZAKY\\IdeaProjects\\Project_JavaWeb_Bioskop\\Bioskop_Admin\\src\\main\\resources\\static\\heroes";

    public boolean uploadFileHeroes(MultipartFile file) {
        boolean isUpload = false;
        try {
            Files.copy(file.getInputStream(), Paths.get(UPLOAD_FOLDER_HEROES + File.separator + file.getOriginalFilename()) , StandardCopyOption.REPLACE_EXISTING);
            isUpload = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isUpload;
    }

    public boolean checkExistHeroes(MultipartFile multipartFile){
        boolean isExist = false;
        try {
            File file = new File(UPLOAD_FOLDER_HEROES +"\\" + multipartFile.getOriginalFilename());
            isExist = file.exists();
        }catch (Exception e){
            e.printStackTrace();
        }
        return isExist;
    }

    private final String UPLOAD_FOLDER_ADMIN = "C:\\Users\\ABIYYU ZAKY\\IdeaProjects\\Project_JavaWeb_Bioskop\\Bioskop_Admin\\src\\main\\resources\\static\\adminProfile";

    public boolean uploadFileAdminProfile(MultipartFile file) {
        boolean isUpload = false;
        try {
            Files.copy(file.getInputStream(), Paths.get(UPLOAD_FOLDER_ADMIN + File.separator + file.getOriginalFilename()) , StandardCopyOption.REPLACE_EXISTING);
            isUpload = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isUpload;
    }

    public boolean checkExistFileAdminProfile(MultipartFile multipartFile){
        boolean isExist = false;
        try {
            File file = new File(UPLOAD_FOLDER_ADMIN +"\\" + multipartFile.getOriginalFilename());
            isExist = file.exists();
        }catch (Exception e){
            e.printStackTrace();
        }
        return isExist;
    }

}
