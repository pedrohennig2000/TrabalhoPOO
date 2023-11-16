/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import enuns.Receita;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 *
 * @author pedro
 */
public class Conta {

    private double saldoTotal;
    private double saldoAtual;

    private ArrayList<Lancamento> lancamento = new ArrayList<>();

    public ArrayList<Lancamento> getLancamento() {
        return lancamento;
    }

    public void setLancamento(ArrayList<Lancamento> lancamento) {
        this.lancamento = lancamento;
    }

    public double getSaldoAtual() {
        return saldoAtual;
    }

    public double getSaldoTotal() {
        return saldoTotal;
    }

    public void setSaldoTotal(double saldoTotal) {
        this.saldoTotal = saldoTotal;
    }

    private void setSaldoTotal() {

        ArrayList<Lancamento> receita = getReceita();
        Double saldoAtualCalculo = 0.0;
        for (Lancamento saldo : receita) {
            if (saldo instanceof Receita) {
                saldoAtualCalculo += saldo.getValor();
            }
        }
        Conta.this.setSaldoTotal(saldoAtualCalculo - getDespesasTotal());
    }

    public Double getDespesasTotal() {
        ArrayList<Lancamento> despesas = getDespesas();

        Double despesasTotais = 0.0;

        for (Lancamento l : despesas) {
            if (l instanceof Despesas) {
                despesasTotais += l.getValor();
            }
        }
        return despesasTotais;
    }

    public Double getDespesasAtuais() {
        ArrayList<Lancamento> despesas = getDespesas();

        Double despesasAtuais = 0.0;


        for (Lancamento l : despesas) {
            if (l instanceof Despesas) {
                if (l.getData().isBefore(LocalDate.now()) || l.getData().equals(LocalDate.now())) {
                    despesasAtuais += l.getValor();
                }
            }
        }
        return despesasAtuais;
    }

    private void setSaldoAtual() {
        ArrayList<Lancamento> receita = getReceita();
        Double saldoAtualCalculo = 0.0;
        for (Lancamento saldo : receita) {
            if (saldo instanceof Receita) {
                if (saldo.getData().isBefore(LocalDate.now()) || saldo.getData().equals(LocalDate.now())) {
                    saldoAtualCalculo += saldo.getValor();
                }
            }
        }

        this.saldoAtual = saldoAtualCalculo - getDespesasAtuais();
    }

    public ArrayList<Lancamento> getReceita() {

        ArrayList<Lancamento> receita = new ArrayList<>();

        for (Lancamento lancamento : this.lancamento) {
            if (lancamento instanceof Receita) {
                receita.add(lancamento);
            }
        }
        return receita;
    }

    public void inserirReceita(Lancamento receita) {
        if (receita instanceof Receita) {
            lancamento.add(receita);
        } else {
            throw new IllegalArgumentException("Não é uma instancia de Receita");
        }
        setSaldoTotal();
        setSaldoAtual();
    }

    public ArrayList<Lancamento> getDespesas() {
        ArrayList<Lancamento> despesa = new ArrayList<>();
        for (Lancamento lancamento : this.lancamento) {
            if (lancamento instanceof Despesas) {
                despesa.add(lancamento);
            }
        }
        return despesa;
    }

    public void inserirDespesa(Lancamento despesa) {
        if (despesa instanceof Despesas) {
            lancamento.add(despesa);
        } else {
            throw new IllegalArgumentException("Não é uma instancia de Despesa");
        }
    }

}
