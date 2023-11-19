package com.example.exercicio1;

import android.app.AlertDialog;
import android.content.Context;
import androidx.annotation.NonNull;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PetDAO {
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    public void addPet(Pet pet, Context context) {
        Map<String, Object> petData = new HashMap<>();
        petData.put("nome", pet.getNome());
        petData.put("tipo", pet.getTipo());
        petData.put("faixaEtaria", pet.getFaixaEtaria());
        petData.put("raca", pet.getRaca());
        petData.put("sexo", pet.getSexo());
        petData.put("vacinas", pet.getVacinas());
        petData.put("descricao", pet.getDescricao());
        petData.put("porte", pet.getPorte());

        db.collection("pet").add(pet).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        String petId = documentReference.getId();
                        pet.setId(petId);
                        documentReference.update("id", petId);
                        String mensagemSucesso = "Pet Cadastrado!";
                        mostrarDialogo(context, "Sucesso", mensagemSucesso);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        String mensagemErro = "Erro ao cadastrar pet: " + e.getMessage();
                        mostrarDialogo(context, "ERRO", mensagemErro);
                    }
                });
    }

    public void editePet(Pet pet, Context context) {
        if (pet.getId() != null) {
            Map<String, Object> petData = new HashMap<>();
            petData.put("id",pet.getId());
            petData.put("nome", pet.getNome());
            petData.put("tipo", pet.getTipo());
            petData.put("faixaEtaria", pet.getFaixaEtaria());
            petData.put("raca", pet.getRaca());
            petData.put("sexo", pet.getSexo());
            petData.put("vacinas", pet.getVacinas());
            petData.put("descricao", pet.getDescricao());
            petData.put("porte", pet.getPorte());

            db.collection("pet").document(pet.getId()).set(petData)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            String mensagemSucesso = "Pet atualizado!";
                            mostrarDialogo(context, "Sucesso", mensagemSucesso);
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            String mensagemErro = "Erro ao atualizar pet: " + e.getMessage();
                            mostrarDialogo(context, "ERRO", mensagemErro);
                        }
                    });
        } else {
            String mensagemErro = "ID do pet não encontrado. Certifique-se de que o pet foi cadastrado.";
            mostrarDialogo(context, "ERRO", mensagemErro);
        }
    }

    public void removePet(String petId, Context context) {
        if (petId != null && !petId.isEmpty()) {
            db.collection("pet").document(petId).delete()
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            String mensagemSucesso = "Pet excluído com sucesso!";
                            mostrarDialogo(context, "Sucesso", mensagemSucesso);
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            String mensagemErro = "Erro ao excluir pet: " + e.getMessage();
                            mostrarDialogo(context, "ERRO", mensagemErro);
                        }
                    });
        } else {
            String mensagemErro = "ID do pet inválido. Certifique-se de fornecer um ID válido.";
            mostrarDialogo(context, "ERRO", mensagemErro);
        }
    }

    public void getAllPets(Context context, final PetCallback callback) {
        List<Pet> pets = new ArrayList<>();

        db.collection("pet").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                List<Pet> pets = new ArrayList<>();
                for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                    Pet pet = new Pet(documentSnapshot.getString("nome"), documentSnapshot.getString("tipo"), documentSnapshot.getString("faixaEtaria"), documentSnapshot.getString("raca"), documentSnapshot.getString("sexo"));

                    pet.setId(documentSnapshot.getId());
                    pet.setPorte(documentSnapshot.getString("porte"));
                    pet.setCatracao(documentSnapshot.getString("catracao"));
                    pet.setDescricao(documentSnapshot.getString("descricao"));

                    List<String> vacinas = (List<String>) documentSnapshot.get("vacinas");
                    if (vacinas != null) {
                        pet.setVacinas(new ArrayList<>(vacinas));
                    } else {
                        pet.setVacinas(new ArrayList<>());
                    }
                    pets.add(pet);
                }

                callback.onCallback(pets);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                callback.onCallback(null);
            }
        });
    }

    public Pet getPetById(String id, Context context) {
        Pet[] pet = new Pet[1];

        db.collection("pet").document(id).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                pet[0] = documentSnapshot.toObject(Pet.class);
            }
        });

        return pet[0];
    }

    private void mostrarDialogo(Context context, String titulo, String mensagem) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(titulo);
        builder.setMessage(mensagem);
        builder.setPositiveButton("OK", null);
        builder.create().show();
    }

    public interface PetCallback {
        void onCallback(List<Pet> pets);
    }
}
