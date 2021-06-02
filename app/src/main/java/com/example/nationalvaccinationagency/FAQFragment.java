package com.example.nationalvaccinationagency;

import android.os.Bundle;
import android.widget.ExpandableListView;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.nationalvaccinationagency.adapters.FAQAdapter;

import java.util.ArrayList;

public class FAQFragment extends Fragment {
    ExpandableListView expandableListView;
    FAQAdapter faqAdapter;
    ArrayList<String> questionList, answerList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View faqView = inflater.inflate(R.layout.fragment_f_a_q, container, false);
        expandableListView = faqView.findViewById(R.id.faqList);
        questionList = new ArrayList<>();
        answerList = new ArrayList<>();

        questionList.add("Είμαι εγγεγραμμένος στην άυλη συνταγογράφηση και έλαβα SMS με ραντεβού το οποίο με εξυπηρετεί. Τι πρέπει να κάνω;");
        answerList.add("Στα SMS που λάβατε αναφέρεται ένας κωδικός εμβολιασμού. Για να επιβεβαιώσετε το ραντεβού πρέπει να στείλετε με τη σειρά σας ένα SMS στο 13034 γράφοντας μόνο αυτόν τον κωδικό. Τότε θα λάβετε νέο SMS που θα γράφει ότι η διαδικασία ολοκληρώθηκε επιτυχώς. Η προ-κράτηση ραντεβού πρέπει να επιβεβαιωθεί μέσα σε 72 ώρες, αλλιώς παύει να ισχύει.");
        questionList.add("Είμαι εγγεγραμμένος στην άυλη συνταγογράφηση και έλαβα SMS με ραντεβού το οποίο δεν με εξυπηρετεί. Τι πρέπει να κάνω;");
        answerList.add("Μπορείτε να αλλάξετε το ραντεβού που σας προτείνεται είτε στο emvolio.gov.gr, είτε σε φαρμακείο ή ΚΕΠ, με την ίδια διαδικασία που περιγράφεται στην αμέσως επόμενη ερώτηση, δηλαδή με τη διαδικασία που ακολουθούν όσοι δεν ήταν εγγεγραμμένοι στην άυλη συνταγογράφηση.");
        questionList.add("Είμαι άνω των 55 ετών, δεν είμαι εγγεγραμμένος στην άυλη συνταγογράφηση και επιθυμώ να κλείσω ραντεβού εμβολιασμού. Τι πρέπει να κάνω;");
        answerList.add("Έχετε δύο τρόπους: είτε να μπείτε στο emvolio.gov.gr, είτε να απευθυνθείτε σε φαρμακείο ή ΚΕΠ.");
        questionList.add("Είμαι άνω των 55 ετών, αλλά δεν είναι εύκολο να μεταβώ σε ΚΕΠ για να κλείσω ραντεβού. Μπορεί να το κάνει για εμένα κάποιος συγγενής μου;");
        answerList.add("Ναι, γίνεται. Μάλιστα, μπορεί να γίνει και χωρίς εξουσιοδότηση, αρκεί το πρόσωπο που θα κάνει τη διαδικασία για εσάς να υπογράψει υπεύθυνη δήλωση με το ακόλουθο περιεχόμενο:«Εκπροσωπώ τον/ την (ονοματεπώνυμο)   με   (αρ. ταυτότητας ή διαβατηρίου) και (ΑΜΚΑ) προκειμένου να προγραμματίσω το ραντεβού του / της κατά της ασθένειας covid19».Το πρόσωπο που θα προσέλθει πρέπει να έχει μαζί του την ταυτότητά του, ενώ καλό είναι να έχει και ένα απλό αντίγραφο της ταυτότητάς του προσώπου για το οποίο ενεργεί, σε περίπτωση που χρειαστεί κάποιο επιπλέον στοιχείο για τον εντοπισμό του στα μητρώα.");
        questionList.add("Είμαι άνω των 55 ετών, εγγεγραμμένος στην άυλη συνταγογράφηση και δεν έλαβα SMS. Τι πρέπει να κάνω;");
        answerList.add("Αυτό που πρέπει να κάνετε είναι να ελέγξετε ότι έχετε επιλεγεί για την τρέχουσα φάση του εμβολιασμού. Αυτό γίνεται με τρεις τρόπους: είτε στο φαρμακείο ή το ΚΕΠ σας (με ταυτότητα και ΑΜΚΑ), είτε στο emvolio.gov.gr (με ΑΜΚΑ και ΑΦΜ), είτε στέλνοντας SMS στο 13034 με τον ΑΜΚΑ σας. Αν πράγματι επρόκειτο για ζήτημα αριθμού τηλεφώνου, μπορείτε να ακολουθήσετε τη διαδικασία κλεισίματος ραντεβού που περιγράφεται στην προ-προηγούμενη ερώτηση.");
        questionList.add("Αν και είμαι άνω των 55 ετών, έκανα έλεγχο προτεραιότητας και με εμφανίζει μη επιλεγμένο για εμβολιασμό. Τι πρέπει να κάνω;");
        answerList.add("Θα χρειαστεί να μπείτε στο emvolio.gov.gr/aitisi και να συμπληρώσετε τη φόρμα που θα εμφανιστεί – δεν θα σας ζητηθούν κωδικοί Taxisnet. Η αίτησή σας θα εξεταστεί από την αρμόδια Επιτροπή και, εφόσον πράγματι είχατε δικαίωμα να εμβολιαστείτε την τρέχουσα περίοδο, θα ενημερωθείτε για το πώς θα κλείσετε ραντεβού. Η διαδικασία υποβολής αίτησης μπορεί να γίνει και στα ΚΕΠ. Δώστε ιδιαίτερη προσοχή στα στοιχεία επικοινωνίας που θα δηλώσετε, ώστε να λάβετε την απάντησή σας όσο το δυνατόν γρηγορότερα.");
        questionList.add("Είμαι άνω των 55 ετών και μέχρι σήμερα δεν είχα εγγραφεί στην άυλη συνταγογράφηση. Αν εγγραφώ τώρα, θα λάβω SMS;");
        answerList.add("Δυστυχώς, η διαδικασία της προ-κράτησης για κάθε πληθυσμιακή ομάδα γίνεται μία μόνο φορά, πριν ανοίξουν τα ραντεβού για τη συγκεκριμένη ομάδα. Συνεπώς, αν είστε άνω των 55 ετών, η άυλη συνταγογράφηση μπορεί να σας εξυπηρετήσει για τις συνταγές και τα παραπεμπτικά σας, αλλά όχι για να κλείσετε ραντεβού εμβολιασμού. Ως εκ τούτου, θα πρέπει να ακολουθήσετε τη διαδικασία για τους μη εγγεγραμμένους.Όσοι είτε κάτω των 55 ετών μπορείτε ακόμα να εγγράφεστε στην άυλη συνταγογράφηση και, όταν κληθεί η δική σας πληθυσμιακή ομάδα, θα λάβετε κανονικά SMS με την προ-κράτηση ραντεβού.");
        questionList.add("Έχω κλείσει το ραντεβού μου, αλλά θέλω να το αλλάξω. Τι πρέπει να κάνω;");
        answerList.add("Τα ραντεβού αλλάζουν όπως κλείνονται: είτε στο emvolio.gov.gr, είτε σε φαρμακείο ή ΚΕΠ, με τον ίδιο τρόπο που περιγράφεται πιο πριν. Πρέπει να γνωρίζετε ότι αλλαγή ραντεβού μπορεί να γίνει μόνο μία φορά και μόνο εφόσον το ζητήσετε τουλάχιστον τρεις ημέρες πριν από το αρχικά προγραμματισμένο ραντεβού.");
        questionList.add("Έχω κλείσει το ραντεβού μου, αλλά δεν θυμάμαι πότε είναι. Τι πρέπει να κάνω;");
        answerList.add("Αρχικά, έχει προγραμματίσει να γίνει υπενθύμιση του ραντεβού σας τρεις φορές, με SMS στον αριθμό που μας έχετε δηλώσει: το πρώτο τρεις ημέρες πριν από το ραντεβού, το δεύτερο την παραμονή του ραντεβού και το τρίτο στις 7 πμ την ημέρα του ραντεβού.Αν, παρ’ όλα αυτά, επιθυμείτε να αναζητήσετε το ραντεβού σας οποτεδήποτε, μπορείτε είτε να στείλετε SMS στο 13034 γράφοντας τον ΑΜΚΑ και το επώνυμό σας, είτε να μπείτε στο emvolio.gov.gr (αναζήτηση με ΑΜΚΑ και ΑΦΜ), είτε να απευθυνθείτε σε φαρμακείο ή ΚΕΠ.");
        questionList.add("Τι χρειάζεται να έχω μαζί μου όταν προσέλθω για να εμβολιαστώ;");
        answerList.add("Πρέπει να έχετε μαζί σας την ταυτότητά σας και πρόχειρο τον ΑΜΚΑ σας και τον κωδικό εμβολιασμού. Η εκτύπωση των στοιχείων του εμβολιασμού δεν είναι υποχρεωτική.");

        faqAdapter = new FAQAdapter(faqView.getContext(),questionList,answerList);
        expandableListView.setAdapter(faqAdapter);
        return faqView;
    }
}