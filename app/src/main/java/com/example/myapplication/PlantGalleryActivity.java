package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.SearchView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuItemCompat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class PlantGalleryActivity extends AppCompatActivity {

    GridView gridViewPlant;
    String[] CustomLabels = new String[233];
    List<ItemsModel> itemsList = new ArrayList<>();
    Adapter plantAdapter;

    public static Integer[] images = {
            R.drawable.metalasia_muricata,
            R.drawable.plecostachys_serpyllifolia,
            R.drawable.senecio_burchellii,
            R.drawable.senecio_elegans,
            R.drawable.senecio_halimifolius,
            R.drawable.stoebe_capitata,
            R.drawable.stoebe_fusca,
            R.drawable.stoebe_plumosum,
            R.drawable.ursinia_anthemoides_spp_anthemoides,
            R.drawable.laurentia_secunda,
            R.drawable.lightfootia_diffusa,
            R.drawable.lobelia_coronopifolia,
            R.drawable.lobelia_erinus,
            R.drawable.lobelia_setacea,
            R.drawable.lobostemon_fruticosus,
            R.drawable.monopsis_lutea,
            R.drawable.wahlenbergia_capensis,
            R.drawable.putterlickia_pyracantha,
            R.drawable.exomis_microphylla,
            R.drawable.salicornia_meyeriana,
            R.drawable.sarcocornia_natalensis,
            R.drawable.sarcocornia_perennis,
            R.drawable.androcymbium_eucomoides,
            R.drawable.baeometra_uniflora,
            R.drawable.ornithoglossum_viride,
            R.drawable.wurmbea_sp,
            R.drawable.crassula_brachyphylla,
            R.drawable.crassula_ciliare,
            R.drawable.crassula_glomerata,
            R.drawable.cladium_mariscus,
            R.drawable.cyperus_sp,
            R.drawable.cyperus_textilus,
            R.drawable.ficinia_dunensis,
            R.drawable.ficinia_lateralis,
            R.drawable.ficinia_sp,
            R.drawable.schoenoplectes_litoralis,
            R.drawable.drosera_trinervia,
            R.drawable.erica_ferrea,
            R.drawable.otholobium_fruticans,
            R.drawable.aspalathus_hispida,
            R.drawable.aizoon_sarmentosum,
            R.drawable.galenia_pubescens,
            R.drawable.pharnaceum_elongatum,
            R.drawable.tetragonia_fruticosa,
            R.drawable.rhus_crenata,
            R.drawable.rhus_glauca,
            R.drawable.rhus_laevigata,
            R.drawable.rhus_lucida,
            R.drawable.aponogeton_angustifolius,
            R.drawable.zantedeschia_aethiopica,
            R.drawable.cynanchum_africanum,
            R.drawable.asparagus_aethiopicus,
            R.drawable.asparagus_capensis,
            R.drawable.asparagus_exuvialis,
            R.drawable.asparagus_rubicundus,
            R.drawable.myrsiphyllum_asparagoides,
            R.drawable.bulbine_sp,
            R.drawable.trachyandra_hispida,
            R.drawable.trachyandra_revoluta,
            R.drawable.cotula_myriophylloides,
            R.drawable.ammellus_asteroides,
            R.drawable.arctotheca_calendula,
            R.drawable.athanasia_dentata,
            R.drawable.athanasia_trifurcata,
            R.drawable.cenia_turbinata,
            R.drawable.chrysanthemoides_incana,
            R.drawable.chrysanthemoides_monilifera,
            R.drawable.cotula_coronopifolia,
            R.drawable.cotula_filifolia,
            R.drawable.cotula_vulgaris,
            R.drawable.cullumia_sp,
            R.drawable.dimorphotheca_pluvialis,
            R.drawable.elytropappus_rhinocerotis,
            R.drawable.eriocephalus_africanus,
            R.drawable.felicia_filifolia,
            R.drawable.felicia_tenella,
            R.drawable.helichrysum_cymosum,
            R.drawable.helichrysum_moeserianum,
            R.drawable.helichrysum_patulum,
            R.drawable.helichrysum_tenella,
            R.drawable.leysera_gnaphaloides,
            R.drawable.aspalathus_ternata,
            R.drawable.indigofera_sp,
            R.drawable.lebeckia_sp,
            R.drawable.nylandtia_spinosa,
            R.drawable.otholobium_hirtum,
            R.drawable.psoralea_sp,
            R.drawable.sutherlandia_frutescens,
            R.drawable.frankenia_sp,
            R.drawable.cysticapnos_vesicaria,
            R.drawable.orphium_frutescens,
            R.drawable.sebaea_exacoides,
            R.drawable.geranium_incanum,
            R.drawable.pelargonium_betulinum,
            R.drawable.pelargonium_myrrhifolium,
            R.drawable.pelargonium_triste,
            R.drawable.pelargonium_capitatum,
            R.drawable.albuca_flaccida,
            R.drawable.albuca_spiralis,
            R.drawable.wachendorfia_brachyandra,
            R.drawable.wachendorfia_paniculata,
            R.drawable.wachendorfia_parviflora,
            R.drawable.lachenalia_contaminata,
            R.drawable.lachenalia_rubida,
            R.drawable.ornithogalum_thyrsoides,
            R.drawable.spiloxene_aquatica,
            R.drawable.spiloxene_capensis,
            R.drawable.aristea_africana,
            R.drawable.babiana_angustifolia,
            R.drawable.ferraria_divaricata,
            R.drawable.geissorhiza_aspera,
            R.drawable.gladiolus_cunonius,
            R.drawable.gladiolus_gracilis,
            R.drawable.ixia_dubia,
            R.drawable.lapeirousia_anceps,
            R.drawable.micranthus_tubulosus,
            R.drawable.moraea_fugax,
            R.drawable.moraea_minor,
            R.drawable.romulea_flava,
            R.drawable.romulea_roseus,
            R.drawable.forma_alba,
            R.drawable.sparaxis_bulbifera,
            R.drawable.watsonia_coccinea,
            R.drawable.bolboschoenus_maritimus,
            R.drawable.juncus_bufonius,
            R.drawable.juncus_kraussii,
            R.drawable.scirpus_nodosus,
            R.drawable.triglochin_bulbosa,
            R.drawable.salvia_africana_lutea,
            R.drawable.salvia_lanceolata,
            R.drawable.cissampelos_capensis,
            R.drawable.lampranthus_tenuifolius,
            R.drawable.lampranthus_calcaratus,
            R.drawable.erepsia_dunensis,
            R.drawable.lampranthus_explanatus,
            R.drawable.lampranthus_stenopetalus,
            R.drawable.lampranthus_stenus,
            R.drawable.drosanthemum_anomalum,
            R.drawable.lampranthus_bicolor,
            R.drawable.lampranthus_glaucus,
            R.drawable.lampranthus_reptans,
            R.drawable.carpanthea_pomeridiana,
            R.drawable.carpobrotis_acinaciformis,
            R.drawable.carpobrotis_edulis,
            R.drawable.carpobrotis_sauerae,
            R.drawable.cephalophyllum_dubia,
            R.drawable.conicosia_pugioniformis,
            R.drawable.dorotheanthus_bellidiformis,
            R.drawable.lampranthus_amoenus,
            R.drawable.mesembryanthemum_crystallinum,
            R.drawable.ruschia_macowanii,
            R.drawable.ruschia_pulchella,
            R.drawable.sphalmanthus_canaliculatus,
            R.drawable.nymphaea_capensis,
            R.drawable.acrolophia_bolusii,
            R.drawable.holothrix_villosa,
            R.drawable.monadenia_bracteata,
            R.drawable.pterygodium_catholicum,
            R.drawable.satyrium_coriifolium,
            R.drawable.satyrium_odorum,
            R.drawable.oxalis_hirta,
            R.drawable.oxalis_luteola,
            R.drawable.oxalis_pes_caprae,
            R.drawable.oxalis_purpurea,
            R.drawable.oxalis_versicolor,
            R.drawable.limonium_equisetinum,
            R.drawable.agrostis_avenacea,
            R.drawable.aristida_congesta_ssp_congesta,
            R.drawable.cynodon_dactylon,
            R.drawable.ehrharta_calycina,
            R.drawable.ehrharta_villosa,
            R.drawable.eragrostis_capensis,
            R.drawable.pentaschistus_aeroides,
            R.drawable.pentaschistus_thunbergii,
            R.drawable.phragmites_australis,
            R.drawable.sporobolus_virginicus,
            R.drawable.stenotaphrum_secundatum,
            R.drawable.tribolium_uniolae,
            R.drawable.muraltia_citeroides,
            R.drawable.muraltia_mitior,
            R.drawable.muraltia_satureioides_v_salteri,
            R.drawable.potamogeton_pectinatus,
            R.drawable.leucadendron_levisanus,
            R.drawable.diastella_proteoides,
            R.drawable.serruria_aemula,
            R.drawable.serruria_trilopha,
            R.drawable.leucadendron_levisanus,
            R.drawable.protea_scolymocephala,
            R.drawable.serruria_fasciflora,
            R.drawable.leucadendron_salignum,
            R.drawable.restio_triticeus,
            R.drawable.thamnochortus_erectus,
            R.drawable.thamnochortus_insignis,
            R.drawable.thamnochortus_punctatus,
            R.drawable.elegia_chondropetalum_recta,
            R.drawable.calopsis_rigorata,
            R.drawable.chondropetalum_microcarpum,
            R.drawable.elegia_chondropetalu_nuda,
            R.drawable.elegia_chondropetalum_tectorum,
            R.drawable.elegia_filacea,
            R.drawable.ischyrolepis,
            R.drawable.phylica_cephalantha,
            R.drawable.phylica_ericoides,
            R.drawable.phylica_pubescens,
            R.drawable.phylica_stokoei,
            R.drawable.riella_purpureospora,
            R.drawable.clifforta_ericifolia,
            R.drawable.cliffortia_ethiopica,
            R.drawable.cliffortia_falcata,
            R.drawable.cliffortia_juniperina,
            R.drawable.anthospermum_aethiopicum,
            R.drawable.ruppia_maritima,
            R.drawable.agathosma_apiculata,
            R.drawable.thesium_scabrum,
            R.drawable.dischisma_arenarium,
            R.drawable.manulea_tomentosa,
            R.drawable.nemisa_sp,
            R.drawable.zaluzianskya_divaricata,
            R.drawable.zaluzianskya_villosa,
            R.drawable.selago_sp,
            R.drawable.lycium_afrum,
            R.drawable.hermannia_procumbens_ssp_procumbens,
            R.drawable.hermannia_alnifolia,
            R.drawable.hermannia_pinnata,
            R.drawable.cyanella_hyacinthoides,
            R.drawable.gnidia_lepthantha,
            R.drawable.struthiola_dodecandra,
            R.drawable.gnidia_subulata,
            R.drawable.passerina_vulgaris,
            R.drawable.struthiola_striata,
            R.drawable.typha_capensis,
            R.drawable.pseudalthenia_aschersoniana,
            R.drawable.zygophyllum_flexuosum
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set the content view of the activity to the 'activity_main_plant.xml' layout
        setContentView(R.layout.activity_main_plant);
        setTitle("Plants Catalog");

        // Create a DatabaseAccess instance to access the database
        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(getApplicationContext());
        databaseAccess.open();

        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(getAssets().open("plantsList.txt")));

            // Populate the 'CustomLabels' array using the 'populateNames' method from the databaseAccess
            // The '233' is the maximum number of entries (plants)
            CustomLabels = databaseAccess.populateNames(bufferedReader, 233);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        gridViewPlant = findViewById(R.id.gridViewPlants);

        for (int i = 0; i < CustomLabels.length; i++) {
            // String name = fix(CustomLabels[i]);
            ItemsModel itemsModel = new ItemsModel(CustomLabels[i], images[i]);
            //Toast.makeText(getApplicationContext(),itemsModel.getName(), Toast.LENGTH_LONG).show();
            itemsList.add(itemsModel);
        }

        plantAdapter = new Adapter(itemsList, this, "Plant");
        gridViewPlant.setAdapter(plantAdapter);

        ImageButton returnfromBirdButton = findViewById(R.id.button6);
        returnfromBirdButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an Intent to start homepage activity
                Intent intent = new Intent(PlantGalleryActivity.this, homepage.class);
                startActivity(intent);
            }
        });
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate menu with items using MenuInflator
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);

        // Initialise menu item search bar
        // with id and take its object
        MenuItem menuItem = menu.findItem(R.id.search_view);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(menuItem);
        searchView.setQueryHint("Search by Scientific Name");


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }// This method is called when the user submits a query

            @Override
            public boolean onQueryTextChange(String s) {// This method is called when the text in the searchView changes
                // Get the plantAdapter and apply a filter to it based on the user's input
                plantAdapter.getFilter().filter(s);
                return false;
            }
        });

        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item){// This method handles the selection of menu items
        int id = item.getItemId();
        // Check if the selected item's ID matches the ID of the 'search_view' menu item
        if(id==R.id.search_view){
            // Return true to indicate that we have handled the menu item selection
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}