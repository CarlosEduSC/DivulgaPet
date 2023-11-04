package com.example.exercicio1;

import android.app.AlertDialog;
import android.content.Context;

import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;

import java.util.HashMap;
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

        db.collection("pet").add(pet).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        pet.setId(documentReference.getId());
                        String mensagemErro = "Pet Cadastrado!";
                        AlertDialog.Builder builder = new AlertDialog.Builder(context);
                        builder.setTitle("Erro");
                        builder.setMessage(mensagemErro);
                        builder.setPositiveButton("OK", null);
                        builder.create().show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        String mensagemErro = "Erro ao cadastrar pet: " + e.getMessage();
                        AlertDialog.Builder builder = new AlertDialog.Builder(context);
                        builder.setTitle("Erro");
                        builder.setMessage(mensagemErro);
                        builder.setPositiveButton("OK", null);
                        builder.create().show();
                    }
                });
    }
}
