package com.example.deepnewton

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.deepnewton.databinding.ActivityLoginBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.auth

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.signUpButton.setOnClickListener{
            /*val email = binding.emailEditText.text.toString()
            val password = binding.passwordEditText.text.toString()

            if (email.isEmpty()||password.isEmpty()) {
                Toast.makeText(this,"이메일 또는 패스워드가 입력되지 않았습니다.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener  // 여기서 리스너 실행을 종료
            }
            Firebase.auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(this){ task->
                if(task.isSuccessful){
                    //회원가입 성공
                    Toast.makeText(this,"회원가입에 성공했습니다. 로그인해주세요",Toast.LENGTH_SHORT).show()
                }
                else{
                    //회원가입 실패
                    Toast.makeText(this,"회원가입에 실패했습니다",Toast.LENGTH_SHORT).show()
                }
            }*/

            val intent =Intent(this,SignupActivity::class.java)
            startActivity(intent)

        }

        binding.signInButton.setOnClickListener{
            val email = binding.emailEditText.text.toString()
            val password = binding.passwordEditText.text.toString()

            if (email.isEmpty()||password.isEmpty()) {
                Toast.makeText(this,"이메일 또는 패스워드가 입력되지 않았습니다.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener  // 여기서 리스너 실행을 종료
            }

            Firebase.auth.signInWithEmailAndPassword(email,password).addOnCompleteListener(this){ task->
                if(task.isSuccessful){
                    val intent =Intent(this,MainActivity::class.java)
                    startActivity(intent)
                    finish()
                }
                else{
                    Log.e("LoginActivity",task.exception.toString())
                    Toast.makeText(this,"로그인에 실패했습니다",Toast.LENGTH_SHORT).show()
                }
            }
        }

    }
}
