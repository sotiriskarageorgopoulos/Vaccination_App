package com.example.nationalvaccinationagency;

import android.net.Uri;
import android.os.Bundle;
import android.widget.ScrollView;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.nationalvaccinationagency.adapters.GalleryRecViewAdapter;
import com.example.nationalvaccinationagency.adapters.VideoRecViewAdapter;

import java.util.ArrayList;

public class GalleryFragment extends Fragment {
    private RecyclerView imageRecView, videoRecView;
    private ArrayList<String> imagesPath, youtubeVideos;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View galleryView = inflater.inflate(R.layout.fragment_gallery, container, false);
        createVideoRecView(galleryView);
        createGalleryRecView(galleryView);
        return galleryView;
    }


    /**
     * Δημιουργεί μία συλλογή από εικόνες σε μορφή grid δύο στηλών
     * @param galleryView
     */
    public void createGalleryRecView(View galleryView) {
        imageRecView = galleryView.findViewById(R.id.rec_view_images);
        imagesPath = new ArrayList<>();
        String img1Path = Uri.parse("android.resource://com.example.nationalvaccinationagency/drawable/"+R.drawable.covid_19_gov).toString();
        String img2Path = Uri.parse("android.resource://com.example.nationalvaccinationagency/drawable/"+R.drawable.covid_vaccination).toString();
        String img3Path = Uri.parse("android.resource://com.example.nationalvaccinationagency/drawable/"+R.drawable.covid_vaccination1).toString();
        String img4Path = Uri.parse("android.resource://com.example.nationalvaccinationagency/drawable/"+R.drawable.mask_use).toString();
        String img5Path = Uri.parse("android.resource://com.example.nationalvaccinationagency/drawable/"+R.drawable.vaccines_protection).toString();
        String img6Path = Uri.parse("android.resource://com.example.nationalvaccinationagency/drawable/"+R.drawable.covid_emboliasmos).toString();
        String img7Path = Uri.parse("android.resource://com.example.nationalvaccinationagency/drawable/"+R.drawable.vaccine_image).toString();
        String img8Path = Uri.parse("android.resource://com.example.nationalvaccinationagency/drawable/"+R.drawable.health_ministry).toString();

        imagesPath.add(img1Path);
        imagesPath.add(img2Path);
        imagesPath.add(img3Path);
        imagesPath.add(img4Path);
        imagesPath.add(img5Path);
        imagesPath.add(img6Path);
        imagesPath.add(img7Path);
        imagesPath.add(img8Path);

        GalleryRecViewAdapter galleryAdapter = new GalleryRecViewAdapter(galleryView.getContext());
        galleryAdapter.setImagesPath(imagesPath);
        imageRecView.setAdapter(galleryAdapter);
        imageRecView.setLayoutManager(new GridLayoutManager(galleryView.getContext(),2));
    }

    /**
     * Δημιουργεί μία συλλογή από βίντεο
     * @param galleryView
     */
    public void createVideoRecView(View galleryView) {
        videoRecView = galleryView.findViewById(R.id.rec_view_videos);
        youtubeVideos = new ArrayList<>();
        youtubeVideos.add("android.resource://com.example.nationalvaccinationagency/raw/" + R.raw.covid_info);
        youtubeVideos.add("android.resource://com.example.nationalvaccinationagency/raw/" + R.raw.distances);

        VideoRecViewAdapter videoRecViewAdapter = new VideoRecViewAdapter(galleryView.getContext());
        videoRecViewAdapter.setVideos(youtubeVideos);
        videoRecView.setAdapter(videoRecViewAdapter);
        videoRecView.setLayoutManager(new LinearLayoutManager(galleryView.getContext()));
    }
}