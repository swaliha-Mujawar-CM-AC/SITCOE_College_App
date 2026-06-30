package com.empire.sitpoly.ui.home;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.empire.sitpoly.R;

import com.empire.sitpoly.adapter.SliderAdapterExample;
import com.google.firebase.database.snapshot.ValueIndex;
import com.smarteist.autoimageslider.IndicatorAnimations;
import com.smarteist.autoimageslider.IndicatorView.draw.controller.DrawController;
import com.smarteist.autoimageslider.SliderAnimations;

import com.smarteist.autoimageslider.SliderView;


public class HomeFragment extends Fragment {


    private SliderView sliderView;
    private ImageView map;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        map = view.findViewById(R.id.map);
        map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openMap();
            }
        });

        sliderView = view.findViewById(R.id.imageSlider);

        final SliderAdapterExample adapter = new SliderAdapterExample(getContext());
        adapter.setCount(4);

        sliderView.setSliderAdapter(adapter);

        sliderView.setIndicatorAnimation(IndicatorAnimations.SLIDE); //set indicator animation by using SliderLayout.IndicatorAnimations. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
        sliderView.setSliderTransformAnimation(SliderAnimations.FADETRANSFORMATION);
        sliderView.setAutoCycleDirection(SliderView.LAYOUT_DIRECTION_INHERIT);
        sliderView.setIndicatorSelectedColor(Color.WHITE);
        sliderView.setIndicatorUnselectedColor(Color.GRAY);
        sliderView.setScrollTimeInSec(3);
        sliderView.startAutoCycle();

        sliderView.setOnIndicatorClickListener(new DrawController.ClickListener() {
            @Override
            public void onIndicatorClicked(int position) {
                sliderView.setCurrentPagePosition(position);
            }
        });




        TextView abouttrust =view.findViewById(R.id.abouttrust);
        abouttrust.setText("•\tSharad Institute of Technology College of Engineering,Yadrav(Ichalkaranji)-2009\n\n" +
                "•\tSharad Institute of Technology, POLYTECHNIC, Yadrav(Ichalkaranji)-2008\n\n" +
                "•\tSahkarRatna Shamraoji Patil (Yadravkar) Industrial Training Centre,Yadrav(Ichalkaranji)-2007\n\n" +
                "•\tSharad College of Agriculture, Jainapur\n\n" +
                "•\tDanling High School, Umalwad\n\n" +
                "•\tDyanganga High School, Jaysingpur\n\n" +
                "•\tDyanganga VidhyaMandir, Jaysingpur\n\n" +
                "•\tDyanganga Balvikas Mandir, Jaysingpur\n\n" +
                "•\tSharad Play Group & English Medium School, Yadrav(Ichalkaranji)-2009\n\n" +
                "•\tSharad Science and Commerce college, Yadrav(Ichalkaranji)-2015\n\n" +
                "\n");

        TextView mission = view.findViewById(R.id.mission);
        mission.setText("•\tTo impart quality education by implementing state-of- the-art teaching-learning methods to enrich the academic competency, credibility and integrity of the students.\n" +
                "\n" +
                "•\tTo facilitate a conducive ambience and infrastructure to develop professional skills and nurture innovation in students.\n" +
                "\n" +
                "\n" +
                "•\tTo inculcate sensitivity towards society, respect for environment and promote high standards of ethics.\n" +
                "\n");

        TextView chairman = view.findViewById(R.id.chairman);
        chairman.setText("•\tMinister Of State For Public Health & Family Welfare, Medical Education, Food & Drug Administration, Textile, Culture Affairs Government Of Maharashtra.\n" +
                "\n" +
                "•\tMLA – Shirol Constituency\n" +
                "Hon’ble Shri Dr. Rajendra Patil (Yadravkar) A visionary & dynamic leader who is a  founder Chairman of Sharad Institute of Technlogy.\n" +
                "His in depth knowledge in sectors like Sugar Industry, Spinning mills, Banks , Education, Agriculture and Health has helped into financial and material well- being of around 60,000 farmers across 150+ villages.\n" +
                "He is well known personality in the region and holds responsible position in surrounding Industries and co-operative sector as stated below:\n" +
                "•\tIndustrial\n\n" +
                "•\tChairman: Sharad Sahakari Sakhar Karkhana Ltd., Shree Shamrao Patil (Yadravkar) Nagar, Narande Tal- Hatkangale Dist- Kolhapur\n" +
                "•\tChairman: Parvati Co-op Industrial Estate Ltd., Yadrav Tal- Shirol Dist- Kolhapur\n" +
                "•\tVice President: The All India Federation of Co-op Spinning Mills Ltd., New Delhi\n" +
                "•\tDirector: Kolhapur Zilla Shetkari Vinkari Sahakari Soot Girani Ltd.,Yadrav\n" +
                "•\tDirector: Parvati Co-operative Soot Girni Ltd.,Kurundwad.\n" +
                "•\tEducational\n" +
                "•\tChairman: Shree Shamrao Patil (Yadravkar) Educational & Charitable Trust’s.\n" +
                "•\tFinancial\n" +
                "•\tChairman: Yadrav Co-operative Bank Ltd., Yadrav\n" +
                "•\tDirector: Kolhapur District Co-operative Bank Ltd., Kolhapur\n" +
                "\n");

        TextView executivedirector =view.findViewById(R.id.executivedirector);
        executivedirector.setText("Hon. Mr. Anil Bagane is the Executive Director of Sharad Institute of Technology devoting efforts and dynamic working style had proven an outstanding team leadership and management. He is a Reputed Industrialist and also contributing as a vice Chairman of Parvati Industrial Estate & Manufacturing Association, Yadrav (PIEMA)\n" +
                "\n" +
                "These are exciting times to say the least! We face immense challenges that have impacted the financial services industry and the effects of these challenges will be felt for many years to come. The current economic woes are being felt in every Institute – no one seems immune. That being said, I believe that if we are nimble and creative we can find opportunity in the face of changes in adversity.\n" +
                "\n" +
                "We have a vision to develop excellent center of technical education coupled with industrial interaction, We are insisting on the overall development of our students such as emphasis on moral and cultural values So that they will be perfect educationally as well as technically.\n" +
                "\n" +
                "The new vision is very comprehensive, perhaps even aggressive, and is very achievable. Even today. One of the cornerstones of the vision is to position SIT Institute of Technology as a Thought Leader. If one looks across the landscape of the organization, they begin to realize the immense amount of talent, knowledge and resources that our members bring to the table. We have a unique opportunity to capture these resources and position SIT Institute of Technology and our members as Thought Leaders in the field of technical education.\n" +
                "\n" +
                "Achievement of the goals outlined in the strategic vision is very exciting and we have moved forward on several goals already. I am honored to work with a very dedicated group of men and women who works together to spread the quality knowledge. These staff members, have devoted so many hours collectively over the past year, outside of their day jobs, to insure that the strategic vision is realized. These staff members bring an immense amount of talent, resources and experience to the table for the benefit of SIT Institute of Technology and its membership. SIT Institute of Technology as a Thought Leader continues to be our overriding theme for the year – and the journey has begun. I would encourage each and every member to join us on the journey.");
        return view;




    }

    private void openMap() {
        Uri uri = Uri.parse("geo:0,0?q=SIT Polytechnic Yadrav");

        Intent intent = new Intent(Intent.ACTION_VIEW,uri);
        intent.setPackage("com.google.android.apps.maps");
        startActivity(intent);
    }


}