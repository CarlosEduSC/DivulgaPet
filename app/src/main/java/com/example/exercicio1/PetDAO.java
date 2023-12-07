package com.example.exercicio1;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;

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
        petData.put("castracao", pet.getCatracao());
        petData.put("idUsuario", pet.getIdUsuario());
        petData.put("interessados", pet.getInteressados());
        petData.put("foto", pet.getFoto());

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
            petData.put("idUsuario", pet.getIdUsuario());
            petData.put("interessados", pet.getInteressados());
            petData.put("foto", pet.getFoto());

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
                    Pet pet = new Pet(documentSnapshot.getString("nome"), documentSnapshot.getString("tipo"), documentSnapshot.getString("faixaEtaria"), documentSnapshot.getString("raca"), documentSnapshot.getString("sexo"), documentSnapshot.getString("idUsuario"), documentSnapshot.getString("foto"));

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

                    List<String> interessados = (List<String>) documentSnapshot.get("interessados");
                    if (interessados != null) {
                        pet.setInteressados(new ArrayList<>(interessados));
                    } else {
                        pet.setInteressados(new ArrayList<>());
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

    public void getPetById(String id, Context context, GetPetCallback callback) {
        db.collection("pet").document(id).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Pet pet = new Pet(documentSnapshot.getString("nome"), documentSnapshot.getString("tipo"), documentSnapshot.getString("faixaEtaria"), documentSnapshot.getString("raca"), documentSnapshot.getString("sexo"), documentSnapshot.getString("idUsuario"), documentSnapshot.getString("foto"));

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

                List<String> interessados = (List<String>) documentSnapshot.get("interessados");
                if (interessados != null) {
                    pet.setInteressados(new ArrayList<>(interessados));
                } else {
                    pet.setInteressados(new ArrayList<>());
                }
                callback.onCallback(pet);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                callback.onCallback(null);
            }
        });
    }

    public void addInteressadoById(String petId, String interessadoId, Context context) {
        DocumentReference petRef = db.collection("pet").document(petId);

        petRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()) {
                    List<String> interessados = (List<String>) documentSnapshot.get("interessados");

                    if (interessados == null) {
                        interessados = new ArrayList<>();
                    }
                    if (!interessados.contains(interessadoId)) {
                        interessados.add(interessadoId);

                        Map<String, Object> data = new HashMap<>();
                        data.put("interessados", interessados);

                        petRef.update(data)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        String mensagemErro = "Voçe foi adicionado a lista de interessados no pet!.";
                                        mostrarDialogo(context, "AVISO", mensagemErro);
                                    }
                                });

                    } else {
                        String mensagemErro = "Voçe já está incluido na lista de interessados do pet!";
                        mostrarDialogo(context, "AVISO", mensagemErro);
                    }
                } else {
                    String mensagemErro = "Pet não encontrado.";
                    mostrarDialogo(context, "ERRO", mensagemErro);
                }
            }
        });
    }

    public void getPetInteressados(String petId, Context context, GetInteressadosCallback callback) {
        DocumentReference petRef = db.collection("pet").document(petId);

        petRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()) {
                    List<String> interessadosIds = (List<String>) documentSnapshot.get("interessados");

                    if (interessadosIds != null && !interessadosIds.isEmpty()) {
                        List<Usuario> interessados = new ArrayList<>();

                        for (String interessadoId : interessadosIds) {
                            UsuarioDAO usuarioDAO = new UsuarioDAO();

                            usuarioDAO.getUsuarioById(interessadoId, context, new UsuarioDAO.GetUsuarioCallback() {
                                @Override
                                public void onCallback(Usuario usuario) {
                                    int totalInteressados = interessadosIds.size();
                                    int count = 0;
                                    if (usuario != null) {
                                        interessados.add(usuario);
                                    }

                                    count++;

                                    if (count == totalInteressados) {
                                        callback.onCallback(interessados);
                                    }
                                }
                            });
                        }
                    } else {
                        callback.onCallback(null);
                    }
                } else {
                    String mensagemErro = "Pet não encontrado.";
                    mostrarDialogo(context, "ERRO", mensagemErro);
                }
            }
        });
    }

    public interface GetInteressadosCallback {
        void onCallback(List<Usuario> interessados);
    }

    public interface GetPetCallback {
        void onCallback(Pet pet);
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
